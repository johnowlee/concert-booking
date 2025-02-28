package hhplus.concert.application.concert;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@UseCase
public class GetConcertOptionsByConcertIdUseCase {

    private final ConcertOptionReader concertOptionReader;

    public List<ConcertOption> execute(Long concertId) {
        return concertOptionReader.getConcertOptionsByConcertId(concertId);
    }
}
