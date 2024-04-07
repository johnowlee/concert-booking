package hhplus.concert.api.mock.controller;

import hhplus.concert.api.mock.FakeStore;
import hhplus.concert.api.mock.dto.request.UserRequest;
import hhplus.concert.api.mock.dto.request.BookingRequest;
import hhplus.concert.api.mock.dto.request.QueueTokenRequest;
import hhplus.concert.api.mock.dto.response.booking.BookingResponse;
import hhplus.concert.api.mock.dto.response.booking.BookingResultResponse;
import hhplus.concert.api.mock.dto.response.booking.BookingsDTO;
import hhplus.concert.api.mock.dto.response.booking.PaymentResponse;
import hhplus.concert.api.mock.dto.response.concert.ConcertWithSeatsResponse;
import hhplus.concert.api.mock.dto.response.concert.ConcertsResponse;
import hhplus.concert.api.mock.dto.response.user.QueueResponse;
import hhplus.concert.api.mock.dto.response.user.UserWithBalanceResponse;
import hhplus.concert.api.mock.dto.response.user.chargeBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/mock")
public class MockQueueController {

    private final FakeStore fakeStore;

    @GetMapping("/queue/{id}")
    public QueueResponse queue(@PathVariable long id) {
        return fakeStore.createFakeQueue();
    }

    @GetMapping("/concerts")
    public ConcertsResponse concerts() {
        return fakeStore.createConcerts();
    }

    @GetMapping("/concerts/{id}")
    public ConcertWithSeatsResponse concert(@PathVariable long id) {
        return fakeStore.createConcert(id);
    }

    @PostMapping("/concerts/{id}/booking")
    public BookingResultResponse ConcertBooking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                         @PathVariable long id,
                                         @RequestBody BookingRequest bookingRequest) {
        return fakeStore.getBookingResponse(queueTokenRequest, id, bookingRequest);
    }

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

    @GetMapping("/balance/{id}")
    public UserWithBalanceResponse balance(@PathVariable long id) {
        return fakeStore.getBalance(id);
    }

    @PostMapping("/balance/{id}")
    public chargeBalanceResponse booking(@PathVariable long id, @RequestBody UserRequest userRequest) {
        return fakeStore.chargeBalance(id, userRequest);
    }
}
