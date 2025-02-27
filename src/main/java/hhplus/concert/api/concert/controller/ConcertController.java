package hhplus.concert.api.concert.controller;

import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.concert.usecase.GetConcertOptionUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionsUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.api.concert.usecase.response.ConcertOptionWithSeatsResponse;
import hhplus.concert.api.concert.usecase.response.ConcertOptionsResponse;
import hhplus.concert.api.concert.usecase.response.ConcertsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final GetConcertsUseCase getConcertsUseCase;
    private final GetConcertOptionsUseCase getConcertOptionsUseCase;
    private final GetConcertOptionUseCase getConcertOptionUseCase;

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
}
