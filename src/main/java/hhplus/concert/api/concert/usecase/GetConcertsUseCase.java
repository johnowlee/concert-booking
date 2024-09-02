package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.concert.usecase.response.ConcertsResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.models.Concert;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@UseCase
public class GetConcertsUseCase {

    private final ConcertReader concertReader;

    public ConcertsResponse execute() {
        List<Concert> concerts = concertReader.getConcerts();
        return ConcertsResponse.from(concerts);
    }
}
