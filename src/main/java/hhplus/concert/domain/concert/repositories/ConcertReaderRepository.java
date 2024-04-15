package hhplus.concert.domain.concert.repositories;

import hhplus.concert.entities.concert.ConcertEntity;
import hhplus.concert.entities.concert.ConcertOptionEntity;

import java.util.List;

public interface ConcertReaderRepository {

    List<ConcertEntity> getConcerts();

    List<ConcertOptionEntity> getConcertOptions();
}
