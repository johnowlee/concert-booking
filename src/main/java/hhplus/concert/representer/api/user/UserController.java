package hhplus.concert.representer.api.user;

import hhplus.concert.application.user.usecase.*;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.representer.api.RestApiResponse;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import hhplus.concert.representer.api.user.request.BalanceChargeRequest;
import hhplus.concert.representer.api.user.request.ConcertBookingRequest;
import hhplus.concert.representer.api.user.response.BalanceResponse;
import hhplus.concert.representer.api.user.response.BookingResponse;
import hhplus.concert.representer.api.user.response.PaymentResponse;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.user.domain.model.User;
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
    private final BookConcertUseCase bookConcertUseCase;
    private final PayBookingUseCase payBookingUseCase;
    private final GetBalanceUseCase getBalanceUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;

    private final UserControllerMapper mapper;

    @GetMapping("/{userId}/bookings")
    public RestApiResponse<List<BookingResponse>> fetchBookings(@PathVariable Long userId) {
        List<Booking> bookings = getBookingsUseCase.execute(userId);
        return RestApiResponse.ok(mapper.toBookingResponseList(bookings));
    }

    @PostMapping("/{userId}/bookings")
    public RestApiResponse<BookingResponse> bookConcert(@PathVariable Long userId,
                                                        @Valid @RequestBody ConcertBookingRequest request,
                                                        QueueTokenRequest queueTokenRequest) {
        Booking booking = bookConcertUseCase.execute(userId, mapper.toConcertBookingDto(request));
        return RestApiResponse.ok(mapper.toBookingResponse(booking));
    }

    @GetMapping("/{userId}/bookings/{bookingId}")
    public RestApiResponse<BookingResponse> fetchBooking(@PathVariable Long userId,
                                                         @PathVariable Long bookingId) {
        Booking booking = getBookingUseCase.execute(userId, bookingId);
        return RestApiResponse.ok(mapper.toBookingResponse(booking));
    }

    @PostMapping("/{userId}/bookings/{bookingId}/payments")
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
    public RestApiResponse<BalanceResponse> chargeBalance(@PathVariable Long userId,
                                                          @Valid @RequestBody BalanceChargeRequest request) {
        User user = chargeBalanceUseCase.execute(userId, mapper.toBalanceBalanceChargeRequest(request));
        return RestApiResponse.ok(mapper.toBalanceResponse(user));
    }
}
