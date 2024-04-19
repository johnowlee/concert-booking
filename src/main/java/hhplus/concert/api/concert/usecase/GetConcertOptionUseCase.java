package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetConcertOptionUseCase {

    private final ConcertOptionReader concertOptionReader;

    public ConcertOptionResponse excute(Long id) {
        return ConcertOptionResponse.from(concertOptionReader.getConcertOptionById(id));
    }
}
