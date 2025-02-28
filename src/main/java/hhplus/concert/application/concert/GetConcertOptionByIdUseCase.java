package hhplus.concert.application.concert;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.domain.concert.components.ConcertOptionReader;
import hhplus.concert.domain.concert.models.ConcertOption;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@UseCase
public class GetConcertOptionByIdUseCase {

    private final ConcertOptionReader concertOptionReader;

    public ConcertOption execute(Long id) {
        return concertOptionReader.getConcertOptionById(id);
    }
}
