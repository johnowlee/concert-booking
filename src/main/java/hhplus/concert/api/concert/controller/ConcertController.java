package hhplus.concert.api.concert.controller;

import hhplus.concert.api.concert.dto.response.ConcertOptionResponse;
import hhplus.concert.api.concert.dto.response.ConcertResponse;
import hhplus.concert.api.concert.dto.response.ConcertsResponse;
import hhplus.concert.api.concert.usecase.GetConcertByIdUseCase;
import hhplus.concert.api.concert.usecase.GetConcertOptionByIdUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
import hhplus.concert.domain.concert.models.ConcertOption;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final GetConcertsUseCase getConcertsUseCase;
    private final GetConcertByIdUseCase getConcertByIdUseCase;
    private final GetConcertOptionByIdUseCase getConcertOptionByIdUseCase;

    @GetMapping
    public ConcertsResponse concerts() {
        return getConcertsUseCase.excute();
    }

    @GetMapping("{id}")
    public ConcertResponse concert(@PathVariable Long id) {
        return getConcertByIdUseCase.excute(id);
    }

    @GetMapping("/option/{id}")
    public ConcertOptionResponse concertOption(@PathVariable Long id) {
        return getConcertOptionByIdUseCase.excute(id);
    }

}
