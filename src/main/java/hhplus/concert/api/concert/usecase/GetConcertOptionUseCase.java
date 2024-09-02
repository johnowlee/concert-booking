package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.concert.usecase.response.ConcertOptionWithSeatsResponse;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.components.SeatReader;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@UseCase
public class GetConcertOptionUseCase {

    private final ConcertOptionReader concertOptionReader;
    private final SeatReader seatReader;

    public ConcertOptionWithSeatsResponse execute(Long id) {
        return ConcertOptionWithSeatsResponse.of(
                concertOptionReader.getConcertOptionById(id),
                seatReader.getSeatsByConcertOptionId(id)
        );
    }
}
