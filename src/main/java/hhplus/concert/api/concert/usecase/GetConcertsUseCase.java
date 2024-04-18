package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.concerts.ConcertsResponse;
import hhplus.concert.domain.concert.components.ConcertReader;
import hhplus.concert.domain.concert.models.Concert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GetConcertsUseCase {

    private final ConcertReader concertReader;

    public ConcertsResponse excute() {
        List<Concert> concerts = concertReader.getConcerts();
        return ConcertsResponse.from(concerts);
    }
}
