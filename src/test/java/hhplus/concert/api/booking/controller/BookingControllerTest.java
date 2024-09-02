package hhplus.concert.api.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.api.booking.usecase.GetBookingByIdUseCase;
import hhplus.concert.api.booking.usecase.GetBookingsByUserIdUseCase;
import hhplus.concert.api.booking.usecase.PayBookingUseCase;
import hhplus.concert.api.booking.usecase.response.BookingsResponse;
import hhplus.concert.api.common.response.BookingResponse;
import hhplus.concert.api.common.response.PaymentResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.queue.service.QueueService;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private GetBookingsByUserIdUseCase getBookingsByUserIdUseCase;

    @MockBean
    private GetBookingByIdUseCase getBookingByIdUseCase;

    @MockBean
    private PayBookingUseCase payBookingUseCase;

    @MockBean
    private QueueService queueService;

    @DisplayName("유저 예약 목록 조회")
    @Test
    public void getBookingsByUserId() throws Exception {
        // given
        User user = createUser();
        ConcertOption concertOption = ConcertOption.builder().place("stadium").build();
        Seat seat = Seat.builder().seatNo("A-1").concertOption(concertOption).build();
        BookingSeat bookingSeat = BookingSeat.builder().seat(seat).build();

        Booking booking = createBooking(user);
        booking.addBookingSeat(bookingSeat);

        List<Booking> bookings = new ArrayList<>(List.of(booking));

        BookingsResponse bookingsResponse = BookingsResponse.from(bookings);
        given(getBookingsByUserIdUseCase.execute(anyLong())).willReturn(bookingsResponse);

        // expected
        mockMvc.perform(get("/bookings/users/{id}", anyLong())
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bookings[0].bookingStatus").value(COMPLETE.name()))
                .andExpect(jsonPath("$.data.bookings[0].concertTitle").value("아이유 콘서트"))
                .andExpect(jsonPath("$.data.bookings[0].booker.name").value("홍길동"))
                .andExpect(jsonPath("$.data.bookings[0].concertOption.place").value("stadium"));
    }

    @DisplayName("예약 상세 조회")
    @Test
    public void getBookingsByBookingId() throws Exception {
        // given
        User user = createUser();
        ConcertOption concertOption = ConcertOption.builder().place("stadium").build();
        Seat seat = Seat.builder().seatNo("A-1").concertOption(concertOption).build();
        BookingSeat bookingSeat = BookingSeat.builder().seat(seat).build();

        Booking booking = createBooking(user);
        booking.addBookingSeat(bookingSeat);

        BookingResponse bookingResponse = BookingResponse.from(booking);
        given(getBookingByIdUseCase.execute(anyLong())).willReturn(bookingResponse);

        // expected
        mockMvc.perform(get("/bookings/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bookingStatus").value(COMPLETE.name()))
                .andExpect(jsonPath("$.data.concertTitle").value("아이유 콘서트"))
                .andExpect(jsonPath("$.data.booker.name").value("홍길동"))
                .andExpect(jsonPath("$.data.concertOption.place").value("stadium"));
    }

    @DisplayName("예약 결제")
    @Test
    public void payment() throws Exception {
        // given
        User user = createUser();

        Booking booking = createBooking(user);

        Payment payment = Payment.builder()
                .user(user)
                .booking(booking)
                .paymentAmount(10000)
                .build();

        PaymentRequest request = new PaymentRequest(2L);
        PaymentResponse paymentResponse = PaymentResponse.from(payment);
        given(payBookingUseCase.execute(1L, request)).willReturn(paymentResponse);

        // expected
        mockMvc.perform(post("/bookings/{id}/payment", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Queue-Token", "token")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.paymentAmount").value(10000))
                .andExpect(jsonPath("$.data.user.name").value("홍길동"))
                .andExpect(jsonPath("$.data.booking.bookingStatus").value(COMPLETE.name()))
                .andExpect(jsonPath("$.data.booking.concertTitle").value("아이유 콘서트"));
    }

    private static Booking createBooking(User user) {
        return Booking.builder()
                .user(user)
                .concertTitle("아이유 콘서트")
                .bookingStatus(COMPLETE)
                .build();
    }

    private static User createUser() {
        return User.builder()
                .name("홍길동")
                .build();
    }
}