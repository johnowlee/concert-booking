package hhplus.concert.api.concert.controller;

import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.fakeStore.FakeStore;
import hhplus.concert.api.fakeStore.dto.request.BookingRequest;
import hhplus.concert.api.fakeStore.dto.request.QueueTokenRequest;
import hhplus.concert.api.fakeStore.dto.response.booking.BookingResultResponse;
import hhplus.concert.api.fakeStore.dto.response.concert.ConcertWithSeatsResponse;
import hhplus.concert.domain.concert.models.Concert;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final FakeStore fakeStore;
    private final GetConcertsUseCase getConcertsUseCase;

    @GetMapping
    public List<Concert> concerts() {
        return getConcertsUseCase.excute();
    }

    @GetMapping("/{id}")
    public ConcertWithSeatsResponse concert(@PathVariable Long id) {
        return fakeStore.createConcert(id);
    }

    @PostMapping("/{id}/booking")
    public BookingResultResponse ConcertBooking(@RequestHeader("Queue-Token") QueueTokenRequest queueTokenRequest,
                                         @PathVariable Long id,
                                         @RequestBody BookingRequest bookingRequest) {
        return fakeStore.getBookingResponse(queueTokenRequest, id, bookingRequest);
    }
}
