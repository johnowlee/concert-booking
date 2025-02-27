package hhplus.concert.api.user;

import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.api.user.mapper.BookingResponseMapper;
import hhplus.concert.api.user.mapper.PaymentResponseMapper;
import hhplus.concert.api.user.response.BookingResponse;
import hhplus.concert.api.user.response.PaymentResponse;
import hhplus.concert.application.user.GetBookingUseCase;
import hhplus.concert.application.user.GetBookingsUseCase;
import hhplus.concert.application.user.PayBookingUseCase;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: 2025-02-27 test
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final GetBookingsUseCase getBookingsUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final PayBookingUseCase payBookingUseCase;
    private final BookingResponseMapper bookingResponseMapper;
    private final PaymentResponseMapper paymentResponseMapper;

    @GetMapping("/{userId}/bookings")
    public RestApiResponse<List<BookingResponse>> fetchBookings(@PathVariable Long userId) {
        List<Booking> bookings = getBookingsUseCase.execute(userId);
        return RestApiResponse.ok(bookingResponseMapper.toBookingResponseList(bookings));
    }

    @GetMapping("/{userId}/bookings/{bookingId}")
    public RestApiResponse<BookingResponse> fetchBooking(@PathVariable Long userId,
                                                         @PathVariable Long bookingId) {
        Booking booking = getBookingUseCase.execute(userId, bookingId);
        return RestApiResponse.ok(bookingResponseMapper.toBookingResponse(booking));
    }

    @PostMapping("/{userId}/bookings/{bookingId}/payment")
    public RestApiResponse<PaymentResponse> payBooking(@PathVariable Long userId,
                                                       @PathVariable Long bookingId,
                                                       QueueTokenRequest queueTokenRequest) {
        Payment payment = payBookingUseCase.execute(userId, bookingId);
        return RestApiResponse.ok(paymentResponseMapper.toPaymentResponse(payment));
    }
}
