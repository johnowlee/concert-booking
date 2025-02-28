package hhplus.concert.api.concert;

import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.concert.response.ConcertOptionResponse;
import hhplus.concert.api.concert.response.ConcertResponse;
import hhplus.concert.application.concert.GetConcertOptionByIdUseCase;
import hhplus.concert.application.concert.GetConcertOptionsByConcertIdUseCase;
import hhplus.concert.application.concert.GetConcertsUseCase;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
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
