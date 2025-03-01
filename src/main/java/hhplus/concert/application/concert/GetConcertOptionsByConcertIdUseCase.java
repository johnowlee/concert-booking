package hhplus.concert.application.concert;

import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.service.ConcertQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class GetConcertOptionsByConcertIdUseCase {

    private final ConcertQueryService concertQueryService;

    public List<ConcertOption> execute(Long concertId) {
        return concertQueryService.getConcertOptionsByConcertId(concertId);
    }
}
