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

    public List<Concert> getConcerts() {
        return concertQueryPort.getConcerts();
    }

    public ConcertOption getConcertOptionById(Long id) {
        return concertQueryPort.getConcertOptionById(id)
                .orElseThrow(() -> new RestApiException(ConcertErrorCode.CONCERT_OPTION_NOT_FOUND));
    }

    public List<ConcertOption> getConcertOptionsByConcertId(Long concertId) {
        return concertQueryPort.getConcertOptionsByConcertId(concertId);
    }

    public List<Seat> getSeatsByIds(List<Long> ids) {
        return concertQueryPort.getSeatsByIds(ids);
    }

    public List<Seat> getSeatsByConcertOptionId(Long concertOptionId) {
        return concertQueryPort.getSeatsByConcertOptionId(concertOptionId);
    }
}
