package hhplus.concert.api.booking.controller;

import hhplus.concert.api.booking.dto.response.booking.BookingResponse;
import hhplus.concert.api.booking.dto.response.bookings.BookingsResponse;
import hhplus.concert.api.booking.usecase.GetBookingByIdUseCase;
import hhplus.concert.api.booking.usecase.GetBookingsByUserIdUseCase;
import hhplus.concert.api.fakeStore.FakeStore;
import hhplus.concert.api.fakeStore.dto.request.QueueTokenRequest;
import hhplus.concert.api.fakeStore.dto.response.booking.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final FakeStore fakeStore;
    private final GetBookingsByUserIdUseCase getBookingsByUserIdUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;

    @GetMapping("/users/{id}")
    public BookingsResponse bookings(@PathVariable Long id) {
        return getBookingsByUserIdUseCase.excute(id);
    }

    @GetMapping("{id}")
    public BookingResponse booking(@PathVariable Long id) {
        return getBookingByIdUseCase.excute(id);
    }

    @GetMapping("{id}/payment")
    public PaymentResponse booking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                   @PathVariable Long id) {
        return fakeStore.getPaymentResponse(queueTokenRequest, id);
    }
}
