package hhplus.concert.api.booking.controller;

import hhplus.concert.api.fakeStore.FakeStore;
import hhplus.concert.api.fakeStore.dto.request.QueueTokenRequest;
import hhplus.concert.api.fakeStore.dto.response.booking.BookingResponse;
import hhplus.concert.api.fakeStore.dto.response.booking.BookingsDTO;
import hhplus.concert.api.fakeStore.dto.response.booking.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final FakeStore fakeStore;

    @GetMapping("/users/{id}")
    public List<BookingsDTO> bookings(@PathVariable long id) {
        return fakeStore.createBookings(id);
    }

    @GetMapping("{id}")
    public BookingResponse booking(@PathVariable long id) {
        return fakeStore.createBooking(id);
    }

    @GetMapping("{id}/payment")
    public PaymentResponse booking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                   @PathVariable long id) {
        return fakeStore.getPaymentResponse(queueTokenRequest, id);
    }
}
