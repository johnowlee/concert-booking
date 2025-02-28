package hhplus.concert.application.concert;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.models.Concert;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
@UseCase
public class GetConcertsUseCase {

    private final ConcertReader concertReader;

    public List<Concert> execute() {
        return concertReader.getConcerts();
    }
}
