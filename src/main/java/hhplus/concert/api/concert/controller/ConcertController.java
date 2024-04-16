package hhplus.concert.api.concert.controller;

import hhplus.concert.api.concert.dto.response.ConcertOptionResponse;
import hhplus.concert.api.concert.dto.response.ConcertsResponse;
import hhplus.concert.api.concert.usecase.GetConcertOptionByIdUseCase;
import hhplus.concert.api.concert.usecase.GetConcertsUseCase;
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
    private final GetConcertOptionByIdUseCase getConcertOptionByIdUseCase;

    @GetMapping
    public ConcertsResponse concerts() {
        return getConcertsUseCase.excute();
    }

    @GetMapping("/option/{id}")
    public ConcertOptionResponse concertOption(@PathVariable Long id) {
        return getConcertOptionByIdUseCase.excute(id);
    }

}
