package hhplus.concert.application.concert;

import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.service.ConcertQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class GetConcertOptionByIdUseCase {

    private final ConcertQueryService concertQueryService;

    public ConcertOption execute(Long id) {
        return concertQueryService.getConcertOptionById(id);
    }
}
