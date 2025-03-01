package hhplus.concert.core.concert.domain.port;

import hhplus.concert.core.concert.domain.model.Concert;
import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.model.Seat;

import java.util.List;
import java.util.Optional;

public interface ConcertQueryPort {

    List<Concert> getConcerts();

    List<ConcertOption> getConcertOptionsByConcertId(Long concertId);

    Optional<ConcertOption> getConcertOptionById(Long id);

    List<Seat> getSeatsByConcertOptionId(Long concertOptionId);

    List<Seat> getSeatsByIds(List<Long> ids);
}
