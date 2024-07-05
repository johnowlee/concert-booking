package hhplus.concert.api.concert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.common.ResponseResult;
import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.concertBooking.BookingResultResponse;
import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.api.concert.dto.response.concerts.ConcertsResponse;
import hhplus.concert.api.concert.usecase.BookConcertUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.queue.service.TokenValidator;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConcertController.class)
class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private GetConcertsUseCase getConcertsUseCase;

    @MockBean
    private GetConcertOptionsUseCase getConcertOptionsUseCase;

    @MockBean
    private GetConcertOptionUseCase getConcertOptionUseCase;

    @MockBean
    private BookConcertUseCase bookConcertUseCase;

    @MockBean
    private TokenValidator tokenValidator;

    @DisplayName("콘서트 목록 조회")
    @Test
    public void getConcerts() throws Exception {
        // given
        Concert concert = Concert.builder()
                .id(1L)
                .build();
        List<Concert> concerts = new ArrayList<>(List.of(concert));

        ConcertsResponse concertsResponse = ConcertsResponse.from(concerts);
        given(getConcertsUseCase.execute()).willReturn(concertsResponse);

        // expected
        mockMvc.perform(get("/concerts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concerts[0].concertId").value(1L));
    }

    @DisplayName("콘서트 옵션 목록 조회")
    @Test
    public void getConcertOptions() throws Exception {
        // given
        Concert concert = Concert.builder()
                .id(1L)
                .build();
        ConcertOption concertOption = ConcertOption.builder()
                .concert(concert)
                .id(1L)
                .place("월드컵경기장")
                .build();
        
        ConcertOptionsResponse concertOptionsResponse = ConcertOptionsResponse.from(new ArrayList<>(List.of(concertOption)));
        given(getConcertOptionsUseCase.execute(1L)).willReturn(concertOptionsResponse);

        // expected
        mockMvc.perform(get("/concerts/{id}/options", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertOptions[0].concertOptionId").value(1L))
                .andExpect(jsonPath("$.concertOptions[0].place").value("월드컵경기장"));
    }

    @DisplayName("콘서트 옵션 단건 조회")
    @Test
    public void getConcertOption() throws Exception {
        // given
        Concert concert = Concert.builder()
                .id(1L)
                .build();
        Seat seat = Seat.builder()
                .id(5L)
                .seatNo("A1")
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();
        ConcertOption concertOption = ConcertOption.builder()
                .concert(concert)
                .id(1L)
                .place("월드컵경기장")
                .seats(new ArrayList<>(List.of(seat)))
                .build();

        ConcertOptionResponse concertOptionResponse = ConcertOptionResponse.from(concertOption);
        given(getConcertOptionUseCase.execute(1L)).willReturn(concertOptionResponse);

        // expected
        mockMvc.perform(get("/concerts/options/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertOptionId").value(1L))
                .andExpect(jsonPath("$.place").value("월드컵경기장"))
                .andExpect(jsonPath("$.seats[0].seatId").value(5L))
                .andExpect(jsonPath("$.seats[0].seatNo").value("A1"))
                .andExpect(jsonPath("$.seats[0].seatBookingStatus").value(SeatBookingStatus.AVAILABLE.name()));
    }

    @DisplayName("콘서트 예약")
    @Test
    public void bookConcert() throws Exception {
        // given
        User user = User.builder().id(10L).name("홍길동").build();
        Concert concert = Concert.builder()
                .id(1L)
                .build();
        Seat seat = Seat.builder()
                .id(5L)
                .seatNo("A1")
                .seatBookingStatus(SeatBookingStatus.AVAILABLE)
                .build();
        List<Seat> seats = new ArrayList<>(List.of(seat));
        ConcertOption concertOption = ConcertOption.builder()
                .concert(concert)
                .id(1L)
                .place("월드컵경기장")
                .seats(seats)
                .build();
        BookingSeat bookingSeat = BookingSeat.builder()
                .seat(seat)
                .build();
        Booking booking = Booking.builder()
                .id(1L)
                .user(user)
                .concertTitle("아이유콘서트")
                .bookingSeats(new ArrayList<>(List.of(bookingSeat)))
                .build();

        BookingResultResponse bookingResultResponse = BookingResultResponse.succeed(user, booking, concertOption, seats);
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(10L, "A1");
        given(bookConcertUseCase.execute(1L, concertBookingRequest)).willReturn(bookingResultResponse);

        // expected
        mockMvc.perform(post("/concerts/options/{optionId}/booking", concertOption.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(concertBookingRequest))
                        .header("Queue-Token", "token")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingResult").value(ResponseResult.SUCCESS.name()))
                .andExpect(jsonPath("$.bookingUserName").value("홍길동"))
                .andExpect(jsonPath("$.concertTitle").value("아이유콘서트"))
                .andExpect(jsonPath("$.seats[0]").value("A1"));
    }
}