package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.ConcertOption;

import java.util.List;

public interface ConcertOptionReaderRepository {

    ConcertOption getConcertOptionById(Long id);

    List<ConcertOption> getConcertOptionsByConcertId(Long concertId);
}
