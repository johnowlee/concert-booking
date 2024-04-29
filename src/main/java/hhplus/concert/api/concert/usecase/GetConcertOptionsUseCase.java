package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionsResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GetConcertOptionsUseCase {

    private final ConcertOptionReader concertOptionReader;

    public ConcertOptionsResponse excute(Long concertId) {
        return ConcertOptionsResponse.from(concertOptionReader.getConcertOptionsByConcertId(concertId));
    }
}
