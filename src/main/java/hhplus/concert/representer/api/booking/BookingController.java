package hhplus.concert.representer.api.booking;

import hhplus.concert.application.user.usecase.BookConcertUseCase;
import hhplus.concert.application.user.usecase.GetBookingUseCase;
import hhplus.concert.application.user.usecase.GetBookingsUseCase;
import hhplus.concert.application.user.usecase.PayBookingUseCase;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.representer.api.RestApiResponse;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import hhplus.concert.representer.api.booking.request.ConcertBookingRequest;
import hhplus.concert.representer.api.booking.response.BookingResponse;
import hhplus.concert.representer.api.booking.response.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class BookingController {

    private final GetBookingsUseCase getBookingsUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final BookConcertUseCase bookConcertUseCase;
    private final PayBookingUseCase payBookingUseCase;
    private final BookingControllerMapper mapper;

    @PostMapping("/{userId}/bookings")
    public RestApiResponse<BookingResponse> bookConcert(@PathVariable Long userId,
                                                        @Valid @RequestBody ConcertBookingRequest request,
                                                        QueueTokenRequest queueTokenRequest) {
        Booking booking = bookConcertUseCase.execute(userId, mapper.toConcertBookingDto(request));
        return RestApiResponse.ok(mapper.toBookingResponse(booking));
    }

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

    @PostMapping("/{userId}/bookings/{bookingId}/payments")
    public RestApiResponse<PaymentResponse> payBooking(@PathVariable Long userId,
                                                       @PathVariable Long bookingId,
                                                       QueueTokenRequest queueTokenRequest) {
        Payment payment = payBookingUseCase.execute(userId, bookingId);
        return RestApiResponse.ok(mapper.toPaymentResponse(payment));
    }
}
