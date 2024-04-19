package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.ConcertOption;

public interface ConcertOptionReaderRepository {

    public ConcertOption getConcertOptionById(Long id);
}
