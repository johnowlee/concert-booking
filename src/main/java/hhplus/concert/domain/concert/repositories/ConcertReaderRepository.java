package hhplus.concert.domain.concert.repositories;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.entities.concert.ConcertEntity;
import hhplus.concert.entities.concert.ConcertOptionEntity;

import java.util.List;

public interface ConcertReaderRepository {

    List<Concert> getConcerts();

    List<ConcertOptionEntity> getConcertOptions();
}
