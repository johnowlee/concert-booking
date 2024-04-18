package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class ConcertCoreReaderRepository implements ConcertReaderRepository {
    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertOptionJpaRepository concertOptionJpaRepository;

    @Override
    public List<Concert> getConcerts() {
        return concertJpaRepository.findAll();
    }

    @Override
    public ConcertOption getConcertOptionById(Long id) {
        return concertOptionJpaRepository.findConcertOptionById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
