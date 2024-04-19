package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertOptionReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class ConcertOptionCoreReaderRepository implements ConcertOptionReaderRepository {

    private final ConcertOptionJpaRepository concertOptionJpaRepository;

    @Override
    public ConcertOption getConcertOptionById(Long id) {
        return concertOptionJpaRepository.findConcertOptionById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
