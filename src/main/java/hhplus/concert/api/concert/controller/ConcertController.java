package hhplus.concert.api.concert.controller;

import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.concert.controller.request.ConcertBookingRequest;
import hhplus.concert.api.concert.usecase.BookConcertUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.concert.usecase.response.ConcertOptionWithSeatsResponse;
import hhplus.concert.api.concert.usecase.response.BookConcertResponse;
import hhplus.concert.api.concert.usecase.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.api.concert.usecase.response.ConcertsResponse;
import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public RestApiResponse<ConcertsResponse> concerts() {
        return RestApiResponse.ok(getConcertsUseCase.execute());
    }

    @GetMapping("{id}/options")
    public RestApiResponse<ConcertOptionsResponse> concertOptions(@PathVariable Long id) {
        return RestApiResponse.ok(getConcertOptionsUseCase.execute(id));
    }

    @GetMapping("/options/{id}")
    public RestApiResponse<ConcertOptionWithSeatsResponse> concertOption(@PathVariable Long id) {
        return RestApiResponse.ok(getConcertOptionUseCase.execute(id));
    }


    @PostMapping("/options/booking")
    public RestApiResponse<BookConcertResponse> bookConcert(QueueTokenRequest queueTokenRequest,
                                                            @Valid @RequestBody ConcertBookingRequest request) {
        return RestApiResponse.ok(bookConcertUseCase.execute(request));
    }
}
