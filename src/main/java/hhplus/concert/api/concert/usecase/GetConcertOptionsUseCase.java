package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.concert.usecase.response.ConcertOptionsResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@UseCase
public class GetConcertOptionsUseCase {

    private final ConcertOptionReader concertOptionReader;

    public ConcertOptionsResponse execute(Long concertId) {
        return ConcertOptionsResponse.from(concertOptionReader.getConcertOptionsByConcertId(concertId));
    }
}
