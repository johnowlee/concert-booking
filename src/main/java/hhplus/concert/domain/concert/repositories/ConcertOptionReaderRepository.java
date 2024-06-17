package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.ConcertOption;

import java.util.List;
import java.util.Optional;

public interface ConcertOptionReaderRepository {

    Optional<ConcertOption> getConcertOptionById(Long id);

    List<ConcertOption> getConcertOptionsByConcertId(Long concertId);
}
