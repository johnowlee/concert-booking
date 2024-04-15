package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.entities.concert.ConcertEntity;
import hhplus.concert.entities.concert.ConcertOptionEntity;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertCoreReaderRepository implements ConcertReaderRepository {

    private final ConcertJpaRepository concertJpaRepository;
    @Override
    @Query("select c from ConcertEntity c join fetch c.concertOptionEntities ")
    public List<ConcertEntity> getConcerts() {
        return concertJpaRepository.findAll();
    }

    @Override
    public List<ConcertOptionEntity> getConcertOptions() {
        return concertJpaRepository.findAllConcertOptions();
    }
}
