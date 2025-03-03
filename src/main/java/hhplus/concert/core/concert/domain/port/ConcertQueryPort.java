package hhplus.concert.core.concert.domain.port;

import hhplus.concert.core.concert.domain.model.Concert;
import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.model.Seat;

import java.util.List;
import java.util.Optional;

public interface ConcertQueryPort {

    List<Concert> findAllConcerts();

    List<ConcertOption> findAllConcertOptionsByConcertId(Long concertId);

    Optional<ConcertOption> findConcertOptionById(Long id);

    List<Seat> findAllSeatsByConcertOptionId(Long concertOptionId);

    List<Seat> findAllSeatsByIds(List<Long> ids);
}
