package hhplus.concert.api.concert.controller;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.BookingResultResponse;
import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.api.concert.dto.response.concerts.ConcertsResponse;
import hhplus.concert.api.concert.usecase.BookConcertUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final GetConcertsUseCase getConcertsUseCase;
    private final GetConcertOptionUseCase getConcertOptionUseCase;
    private final BookConcertUseCase bookConcertUseCase;

    @GetMapping
    public ConcertsResponse concerts() {
        return getConcertsUseCase.excute();
    }

    @GetMapping("/option/{id}")
    public ConcertOptionResponse concertOption(@PathVariable Long id) {
        return getConcertOptionUseCase.excute(id);
    }

    @PostMapping("/option/{optionId}/booking")
    public BookingResultResponse bookConcert(@RequestHeader("Queue-Token") String queueTokeinId,
                                             @PathVariable Long optionId,
                                             @RequestBody ConcertBookingRequest concertBookingRequest) throws InterruptedException {
        return bookConcertUseCase.excute(queueTokeinId, ConcertBookingRequest.from(optionId, concertBookingRequest.seatId()));
    }
}
