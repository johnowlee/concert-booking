package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.ConcertEntity;

import java.util.List;

public interface ConcertReaderRepository {

    List<ConcertEntity> getConcerts();
}
