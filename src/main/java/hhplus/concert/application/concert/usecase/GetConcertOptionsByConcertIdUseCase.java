package hhplus.concert.application.concert.usecase;

import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.service.ConcertQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetConcertOptionsByConcertIdUseCase {

    private final ConcertQueryService concertQueryService;

    public List<ConcertOption> execute(Long concertId) {
        return concertQueryService.getConcertOptionsByConcertId(concertId);
    }
}

