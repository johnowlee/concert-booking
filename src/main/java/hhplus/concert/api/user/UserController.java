package hhplus.concert.api.user;

import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.api.user.request.BalanceChargeRequest;
import hhplus.concert.api.user.response.BalanceResponse;
import hhplus.concert.api.user.response.BookingResponse;
import hhplus.concert.api.user.response.PaymentResponse;
import hhplus.concert.application.user.*;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import jakarta.validation.Valid;
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
    private final GetBalanceUseCase getBalanceUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;

    private final UserControllerMapper mapper;

    @GetMapping("/{userId}/bookings")
    public RestApiResponse<List<BookingResponse>> fetchBookings(@PathVariable Long userId) {
        List<Booking> bookings = getBookingsUseCase.execute(userId);
        return RestApiResponse.ok(mapper.toBookingResponseList(bookings));
    }

    @GetMapping("/{userId}/bookings/{bookingId}")
    public RestApiResponse<BookingResponse> fetchBooking(@PathVariable Long userId,
                                                         @PathVariable Long bookingId) {
        Booking booking = getBookingUseCase.execute(userId, bookingId);
        return RestApiResponse.ok(mapper.toBookingResponse(booking));
    }

    @PostMapping("/{userId}/bookings/{bookingId}/payment")
    public RestApiResponse<PaymentResponse> payBooking(@PathVariable Long userId,
                                                       @PathVariable Long bookingId,
                                                       QueueTokenRequest queueTokenRequest) {
        Payment payment = payBookingUseCase.execute(userId, bookingId);
        return RestApiResponse.ok(mapper.toPaymentResponse(payment));
    }

    @GetMapping("/{userId}/balance")
    public RestApiResponse<BalanceResponse> fetchBalance(@PathVariable Long userId) {
        User user = getBalanceUseCase.execute(userId);
        return RestApiResponse.ok(mapper.toBalanceResponse(user));
    }

    @PatchMapping("/{userId}/balance")
    public RestApiResponse<BalanceResponse> charge(@PathVariable Long userId,
                                                @Valid @RequestBody BalanceChargeRequest request) {
        User user = chargeBalanceUseCase.execute(userId, mapper.toBalanceBalanceChargeRequest(request));
        return RestApiResponse.ok(mapper.toBalanceResponse(user));
    }
}
