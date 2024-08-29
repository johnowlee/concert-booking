package hhplus.concert.api.concert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.BookConcertUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.concert.usecase.response.ConcertOptionWithSeatsResponse;
import hhplus.concert.api.concert.usecase.response.BookConcertResponse;
import hhplus.concert.api.concert.usecase.response.ConcertOptionsResponse;
import hhplus.concert.api.concert.usecase.response.ConcertsResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.queue.support.TokenValidator;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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
        Concert concert = createConcert();
        List<Concert> concerts = new ArrayList<>(List.of(concert));

        ConcertsResponse concertsResponse = ConcertsResponse.from(concerts);
        given(getConcertsUseCase.execute()).willReturn(concertsResponse);

        // expected
        mockMvc.perform(get("/concerts")
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
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
        Seat seat = createSeat("A1", AVAILABLE, null);
        ConcertOption concertOption = ConcertOption.builder()
                .place("월드컵경기장")
                .build();

        ConcertOptionWithSeatsResponse concertOptionResponse = ConcertOptionWithSeatsResponse.of(concertOption, List.of(seat));
        given(getConcertOptionUseCase.execute(1L)).willReturn(concertOptionResponse);

        // expected
        mockMvc.perform(get("/concerts/options/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.concertOption.place").value("월드컵경기장"))
                .andExpect(jsonPath("$.data.seats[0].seatNo").value("A1"))
                .andExpect(jsonPath("$.data.seats[0].seatBookingStatus").value(AVAILABLE.name()));
    }

    @DisplayName("콘서트 예약")
    @Test
    public void bookConcert() throws Exception {
        // given
        User user = User.builder().name("홍길동").build();
        Concert concert = createConcert();
        ConcertOption concertOption = ConcertOption.builder()
                .concert(concert)
                .place("월드컵경기장")
                .build();

        Seat seat = createSeat("A1", AVAILABLE, concertOption);

        BookingSeat bookingSeat = BookingSeat.builder()
                .seat(seat)
                .build();
        Booking booking = Booking.builder()
                .id(1L)
                .user(user)
                .concertTitle("아이유콘서트")
                .build();
        bookingSeat.setBooking(booking);

        BookConcertResponse bookConcertResponse = BookConcertResponse.from(booking);
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(10L, "1");
        given(bookConcertUseCase.execute(concertBookingRequest)).willReturn(bookConcertResponse);

        // expected
        mockMvc.perform(post("/concerts/options/booking", concertOption.getId())
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

    @DisplayName("콘서트 예약시 유저 아이디가 Null이면 안된다.")
    @Test
    public void bookConcertWithNullUserId() throws Exception {
        // given
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(null, "1");

        // expected
        mockMvc.perform(post("/concerts/options/booking", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(concertBookingRequest))
                        .header("Queue-Token", "token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.name").value("INVALID_FIELD_VALUE"))
                .andExpect(jsonPath("$.message").value("유저 아이디는 필수입니다."));
    }

    @DisplayName("콘서트 예약시 유저 아이디는 양수이다.")
    @Test
    public void bookConcertWithNegativeUserId() throws Exception {
        // given
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(-1L, "1");

        // expected
        mockMvc.perform(post("/concerts/options/booking", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(concertBookingRequest))
                        .header("Queue-Token", "token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.name").value("INVALID_FIELD_VALUE"))
                .andExpect(jsonPath("$.message").value("아이디가 부적합 합니다."));
    }

    @DisplayName("콘서트 예약시 좌석 ID는 필수다.")
    @Test
    public void bookConcertWithNoSeatId() throws Exception {
        // given
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(1L, " ");

        // expected
        mockMvc.perform(post("/concerts/options/booking", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(concertBookingRequest))
                        .header("Queue-Token", "token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.name").value("INVALID_FIELD_VALUE"))
                .andExpect(jsonPath("$.message").value("좌석 ID가 유효하지 않습니다."));
    }

    @DisplayName("콘서트 예약시 좌석 ID는 양수이어야 한다.")
    @Test
    public void bookConcertWithInvalidSeatId() throws Exception {
        // given
        ConcertBookingRequest concertBookingRequest = new ConcertBookingRequest(1L, "a,b,2");

        // expected
        mockMvc.perform(post("/concerts/options/booking", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(concertBookingRequest))
                        .header("Queue-Token", "token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.name").value("INVALID_FIELD_VALUE"))
                .andExpect(jsonPath("$.message").value("좌석 ID가 유효하지 않습니다."));
    }

    private static Concert createConcert() {
        return Concert.builder()
                .title("아이유 콘서트")
                .organizer("아이유")
                .build();
    }

    private static Seat createSeat(String seatNo, SeatBookingStatus seatBookingStatus, ConcertOption concertOption) {
        return Seat.builder()
                .seatNo(seatNo)
                .seatBookingStatus(seatBookingStatus)
                .concertOption(concertOption)
                .build();
    }
}