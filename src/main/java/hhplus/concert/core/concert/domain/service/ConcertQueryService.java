package hhplus.concert.core.concert.domain.service;

import hhplus.concert.core.concert.domain.model.Concert;
import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.model.Seat;
import hhplus.concert.core.concert.domain.port.ConcertQueryPort;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.ConcertErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertQueryService {

    private final ConcertQueryPort concertQueryPort;

    public List<Concert> findConcerts() {
        return concertQueryPort.findAllConcerts();
    }

    public ConcertOption getConcertOptionById(Long id) {
        return concertQueryPort.findConcertOptionById(id)
                .orElseThrow(() -> new RestApiException(ConcertErrorCode.CONCERT_OPTION_NOT_FOUND));
    }

    public List<ConcertOption> findConcertOptionsByConcertId(Long concertId) {
        return concertQueryPort.findAllConcertOptionsByConcertId(concertId);
    }

    public List<Seat> findSeatsByIds(List<Long> ids) {
        return concertQueryPort.findAllSeatsByIds(ids);
    }

    public List<Seat> findSeatsByConcertOptionId(Long concertOptionId) {
        return concertQueryPort.findAllSeatsByConcertOptionId(concertOptionId);
    }
}
