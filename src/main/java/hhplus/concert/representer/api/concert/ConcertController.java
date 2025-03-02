package hhplus.concert.representer.api.concert;

import hhplus.concert.representer.api.RestApiResponse;
import hhplus.concert.representer.api.concert.response.ConcertOptionResponse;
import hhplus.concert.representer.api.concert.response.ConcertResponse;
import hhplus.concert.application.concert.usecase.GetConcertOptionByIdUseCase;
import hhplus.concert.application.concert.usecase.GetConcertOptionsByConcertIdUseCase;
import hhplus.concert.application.concert.usecase.GetConcertsUseCase;
import hhplus.concert.core.concert.domain.model.Concert;
import hhplus.concert.core.concert.domain.model.ConcertOption;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concerts")
public class ConcertController {

    private final GetConcertsUseCase getConcertsUseCase;
    private final GetConcertOptionsByConcertIdUseCase getConcertOptionsByConcertIdUseCase;
    private final GetConcertOptionByIdUseCase getConcertOptionByIdUseCase;

    private final ConcertControllerMapper mapper;

    @GetMapping
    public RestApiResponse<List<ConcertResponse>> fetchConcerts() {
        List<Concert> concerts = getConcertsUseCase.execute();
        return RestApiResponse.ok(mapper.toConcertResponseList(concerts));
    }

    @GetMapping("{id}/options")
    public RestApiResponse<List<ConcertOptionResponse>> fetchConcertOptions(@PathVariable Long id) {
        List<ConcertOption> concertOptions = getConcertOptionsByConcertIdUseCase.execute(id);
        return RestApiResponse.ok(mapper.toConcertOptionResponseList(concertOptions));
    }

    @GetMapping("/options/{id}")
    public RestApiResponse<ConcertOptionResponse> fetchConcertOption(@PathVariable Long id) {
        ConcertOption concertOption = getConcertOptionByIdUseCase.execute(id);
        return RestApiResponse.ok(mapper.toConcertOptionResponse(concertOption));
    }
}
