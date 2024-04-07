package hhplus.concert.api.booking.controller;

import hhplus.concert.api.mock.FakeStore;
import hhplus.concert.api.mock.dto.request.QueueTokenRequest;
import hhplus.concert.api.mock.dto.response.booking.BookingResponse;
import hhplus.concert.api.mock.dto.response.booking.BookingsDTO;
import hhplus.concert.api.mock.dto.response.booking.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MockQueueController {

    private final FakeStore fakeStore;

    @GetMapping("/users/{id}/bookings")
    public List<BookingsDTO> bookings(@PathVariable long id) {
        return fakeStore.createBookings(id);
    }

    @GetMapping("/users/{userId}/bookings/{bookingId}")
    public BookingResponse booking(@PathVariable long userId, @PathVariable long bookingId) {
        return fakeStore.createBooking(userId, bookingId);
    }

    @GetMapping("/bookings/{id}/payment")
    public PaymentResponse booking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                   @PathVariable long id) {
        return fakeStore.getPaymentResponse(queueTokenRequest, id);
    }
}
