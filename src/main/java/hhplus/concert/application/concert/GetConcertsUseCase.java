package hhplus.concert.application.concert;

import hhplus.concert.core.concert.domain.model.Concert;
import hhplus.concert.core.concert.domain.service.ConcertQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GetConcertsUseCase {

    private final ConcertQueryService concertQueryService;

    public List<Concert> execute() {
        return concertQueryService.getConcerts();
    }
}
