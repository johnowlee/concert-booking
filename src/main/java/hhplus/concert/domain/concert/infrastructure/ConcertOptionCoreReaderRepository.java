package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.repositories.ConcertOptionReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertOptionCoreReaderRepository implements ConcertOptionReaderRepository {

    private final ConcertOptionJpaRepository concertOptionJpaRepository;

    @Override
    public Optional<ConcertOption> getConcertOptionById(Long id) {
        return concertOptionJpaRepository.findConcertOptionById(id);
    }

    @Override
    public List<ConcertOption> getConcertOptionsByConcertId(Long concertId) {
        return concertOptionJpaRepository.findAllByConcertId(concertId);
    }
}
