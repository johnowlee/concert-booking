package hhplus.concert.api.concert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.BookConcertUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.concert.usecase.response.concertBooking.BookingResultResponse;
import hhplus.concert.api.concert.usecase.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.api.concert.usecase.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.api.concert.usecase.response.concerts.ConcertsResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.queue.support.TokenValidator;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static hhplus.concert.domain.concert.models.SeatBookingStatus.AVAILABLE;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .title("아이유 콘서트")
                .organizer("아이유")
                .build();
        List<Concert> concerts = new ArrayList<>(List.of(concert));

        ConcertsResponse concertsResponse = ConcertsResponse.from(concerts);
        given(getConcertsUseCase.execute()).willReturn(concertsResponse);

        // expected
        mockMvc.perform(get("/concerts")
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concerts[0].concertId").value(1L))
                .andExpect(jsonPath("$.data.concerts[0].title").value("아이유 콘서트"))
                .andExpect(jsonPath("$.data.concerts[0].organizer").value("아이유"));
    }

    @DisplayName("콘서트 옵션 목록 조회")
    @Test
    public void getConcertOptions() throws Exception {
        // given
        ConcertOption concertOption = ConcertOption.builder()
                .place("월드컵경기장")
                .build();
        
        ConcertOptionsResponse concertOptionsResponse = ConcertOptionsResponse.from(List.of(concertOption));
        given(getConcertOptionsUseCase.execute(1L)).willReturn(concertOptionsResponse);

        // expected
        mockMvc.perform(get("/concerts/{id}/options", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertOptions[0].place").value("월드컵경기장"));
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
                .seatBookingStatus(AVAILABLE)
                .build();
        ConcertOption concertOption = ConcertOption.builder()
                .concert(concert)
                .place("월드컵경기장")
                .build();

        ConcertOptionResponse concertOptionResponse = ConcertOptionResponse.from(concertOption, List.of(seat));
        given(getConcertOptionUseCase.execute(1L)).willReturn(concertOptionResponse);

        // expected
        mockMvc.perform(get("/concerts/options/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.place").value("월드컵경기장"))
                .andExpect(jsonPath("$.data.seats[0].seatId").value(5L))
                .andExpect(jsonPath("$.data.seats[0].seatNo").value("A1"))
                .andExpect(jsonPath("$.data.seats[0].seatBookingStatus").value(AVAILABLE.name()));
    }

    @DisplayName("콘서트 예약")
    @Test
    public void bookConcert() throws Exception {
        // given
        User user = User.builder().name("홍길동").build();
        Concert concert = Concert.builder()
                .id(1L)
                .build();

        ConcertOption concertOption = ConcertOption.builder()
                .concert(concert)
                .place("월드컵경기장")
                .build();

        Seat seat = Seat.builder()
                .id(5L)
                .seatNo("A1")
                .seatBookingStatus(AVAILABLE)
                .concertOption(concertOption)
                .build();
        List<Seat> seats = List.of(seat);

        BookingSeat bookingSeat = BookingSeat.builder()
                .seat(seat)
                .build();
        Booking booking = Booking.builder()
                .id(1L)
                .user(user)
                .concertTitle("아이유콘서트")
                .build();
        booking.addBookingSeat(bookingSeat);

        BookingResultResponse bookingResultResponse = BookingResultResponse.of(user, booking, seats);
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(10L, "A1");
        given(bookConcertUseCase.execute(concertBookingRequest)).willReturn(bookingResultResponse);

        // expected
        mockMvc.perform(post("/concerts/options/{optionId}/booking", concertOption.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(concertBookingRequest))
                        .header("Queue-Token", "token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bookerName").value("홍길동"))
                .andExpect(jsonPath("$.data.concertTitle").value("아이유콘서트"))
                .andExpect(jsonPath("$.data.seats[0]").value("A1"));
    }
}