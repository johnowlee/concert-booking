package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertCoreReaderRepository implements ConcertReaderRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Concert> getConcerts() {
        return concertJpaRepository.findAll();
    }


}
