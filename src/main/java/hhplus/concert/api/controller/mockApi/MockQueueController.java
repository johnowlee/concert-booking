package hhplus.concert.api.controller.mockApi;

import hhplus.concert.api.dto.request.UserRequest;
import hhplus.concert.api.dto.request.BookingRequest;
import hhplus.concert.api.dto.request.PaymentRequest;
import hhplus.concert.api.dto.request.QueueTokenRequest;
import hhplus.concert.api.dto.response.booking.BookingResponse;
import hhplus.concert.api.dto.response.booking.PaymentResponse;
import hhplus.concert.api.dto.response.concert.ConcertsResponse;
import hhplus.concert.api.dto.response.user.QueueResponse;
import hhplus.concert.api.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mock")
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

    @PostMapping("/booking")
    public BookingResponse booking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                   @RequestBody BookingRequest bookingRequest) {
        return fakeStore.getBookingResponse(queueTokenRequest, bookingRequest);
    }

    @PostMapping("/payment")
    public PaymentResponse booking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                   @RequestBody PaymentRequest paymentRequest) {
        return fakeStore.getPaymentResponse(queueTokenRequest, paymentRequest);
    }

    @GetMapping("/balance/{id}")
    public UserResponse balance(@PathVariable long id) {
        return fakeStore.getBalance(id);
    }

    @PostMapping("/balance")
    public UserResponse booking(@RequestBody UserRequest userRequest) {
        return fakeStore.chargeBalance(userRequest);
    }
}
