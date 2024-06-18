package hhplus.concert.api.concert.controller;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.api.concert.dto.response.concertBooking.BookingResultResponse;
import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.api.concert.dto.response.concerts.ConcertsResponse;
import hhplus.concert.api.concert.usecase.BookConcertUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.queue.dto.request.QueueTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final GetConcertsUseCase getConcertsUseCase;
    private final GetConcertOptionsUseCase getConcertOptionsUseCase;
    private final GetConcertOptionUseCase getConcertOptionUseCase;
    private final BookConcertUseCase bookConcertUseCase;

    @GetMapping
    public ResponseEntity<ConcertsResponse> concerts() {
        return ResponseEntity.ok().body(getConcertsUseCase.execute());
    }

    @GetMapping("{id}/options")
    public ResponseEntity<ConcertOptionsResponse> concertOptions(@PathVariable Long id) {
        return ResponseEntity.ok().body(getConcertOptionsUseCase.execute(id));
    }

    @GetMapping("/options/{id}")
    public ResponseEntity<ConcertOptionResponse> concertOption(@PathVariable Long id) {
        return ResponseEntity.ok().body(getConcertOptionUseCase.execute(id));
    }


    @PostMapping("/options/{optionId}/booking")
    public ResponseEntity<BookingResultResponse> bookConcert(QueueTokenRequest queueTokenRequest,
                                                             @PathVariable Long optionId,
                                                             @RequestBody ConcertBookingRequest concertBookingRequest) {
        return ResponseEntity.ok().body(bookConcertUseCase.execute(optionId, concertBookingRequest));
    }
}
