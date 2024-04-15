package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import hhplus.concert.entities.concert.ConcertOptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ConcertCoreReaderRepository implements ConcertReaderRepository {

    private final ConcertJpaRepository concertJpaRepository;
    @Override
    @Query("select c from ConcertEntity c" +
            " join fetch c.concertOptionEntities co" +
            " join fetch co.seatEntities")
    public List<Concert> getConcerts() {
        return concertJpaRepository.findAll().stream()
                .map(ce -> ce.toConcert())
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcertOptionEntity> getConcertOptions() {
        return concertJpaRepository.findAllConcertOptions();
    }
}
