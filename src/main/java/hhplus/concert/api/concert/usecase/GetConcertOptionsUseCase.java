package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetConcertOptionsUseCase {

    private final ConcertOptionReader concertOptionReader;

    public ConcertOptionsResponse excute(Long concertId) {
        return ConcertOptionsResponse.from(concertOptionReader.getConcertOptionsByConcertId(concertId));
    }
}
