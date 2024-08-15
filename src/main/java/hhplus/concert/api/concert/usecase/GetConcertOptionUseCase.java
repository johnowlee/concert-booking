package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.usecase.response.concertOptions.ConcertOptionResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GetConcertOptionUseCase {

    private final ConcertOptionReader concertOptionReader;
    private final SeatReader seatReader;

    public ConcertOptionResponse execute(Long id) {
        return ConcertOptionResponse.from(
                concertOptionReader.getConcertOptionById(id),
                seatReader.getSeatsByConcertOptionId(id)
        );
    }
}
