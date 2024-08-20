package hhplus.concert.api.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.api.booking.usecase.response.BookingsResponse;
import hhplus.concert.api.booking.usecase.GetBookingByIdUseCase;
import hhplus.concert.api.booking.usecase.GetBookingsByUserIdUseCase;
import hhplus.concert.api.booking.usecase.PayBookingUseCase;
import hhplus.concert.api.common.response.BookingResponse;
import hhplus.concert.api.common.response.PaymentResponse;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.queue.support.TokenValidator;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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
    private TokenValidator tokenValidator;

    @DisplayName("유저 예약 목록 조회")
    @Test
    public void getBookingsByUserId() throws Exception {
        // given
        Booking booking = Booking.builder()
                .id(1L)
                .build();
        List<Booking> bookings = new ArrayList<>(List.of(booking));

        BookingsResponse bookingsResponse = BookingsResponse.from(bookings);
        given(getBookingsByUserIdUseCase.execute(anyLong())).willReturn(bookingsResponse);

        // expected
        mockMvc.perform(get("/bookings/users/{id}", anyLong())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookings[0].bookingId").value(1L));
    }

    @DisplayName("예약 상세 조회")
    @Test
    public void getBookingsByBookingId() throws Exception {
        // given
        User user = User.builder().name("홍길동").build();
        Seat seat = Seat.builder()
                .concertOption(ConcertOption.builder().build())
                .build();
        BookingSeat bookingSeat = BookingSeat.builder()
                .seat(seat)
                .build();
        Booking booking = Booking.builder()
                .id(1L)
                .user(user)
                .build();
        booking.addBookingSeat(bookingSeat);

        BookingResponse bookingResponse = BookingResponse.from(booking);
        given(getBookingByIdUseCase.execute(anyLong())).willReturn(bookingResponse);

        // expected
        mockMvc.perform(get("/bookings/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.booking.bookingId").value(1L))
                .andExpect(jsonPath("$.bookingUser.userId").value(10L))
                .andExpect(jsonPath("$.bookingUser.name").value("홍길동"));
    }

    @DisplayName("예약 결제")
    @Test
    public void payment() throws Exception {
        // given
        Payment payment = Payment.builder().build();
        PaymentRequest request = new PaymentRequest(2L);
        PaymentResponse paymentResponse = PaymentResponse.from(payment);
        given(payBookingUseCase.execute(1L, request)).willReturn(paymentResponse);

        // expected
        mockMvc.perform(post("/bookings/{id}/payment", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PaymentRequest(10L)))
                        .header("Queue-Token", "token")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentResult").value(payment));
    }
}