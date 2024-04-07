package hhplus.concert.api.concert.controller;

import hhplus.concert.api.mock.FakeStore;
import hhplus.concert.api.mock.dto.request.BookingRequest;
import hhplus.concert.api.mock.dto.request.QueueTokenRequest;
import hhplus.concert.api.mock.dto.response.booking.BookingResultResponse;
import hhplus.concert.api.mock.dto.response.concert.ConcertWithSeatsResponse;
import hhplus.concert.api.mock.dto.response.concert.ConcertsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class MockQueueController {

    private final FakeStore fakeStore;

    @GetMapping("/")
    public ConcertsResponse concerts() {
        return fakeStore.createConcerts();
    }

    @GetMapping("{id}")
    public ConcertWithSeatsResponse concert(@PathVariable long id) {
        return fakeStore.createConcert(id);
    }

    @PostMapping("{id}/booking")
    public BookingResultResponse ConcertBooking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                         @PathVariable long id,
                                         @RequestBody BookingRequest bookingRequest) {
        return fakeStore.getBookingResponse(queueTokenRequest, id, bookingRequest);
    }
}
