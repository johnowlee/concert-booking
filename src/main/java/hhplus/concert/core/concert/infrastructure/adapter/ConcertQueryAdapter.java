package hhplus.concert.core.concert.infrastructure.adapter;

import hhplus.concert.core.concert.domain.model.Concert;
import hhplus.concert.core.concert.domain.model.ConcertOption;
import hhplus.concert.core.concert.domain.model.Seat;
import hhplus.concert.core.concert.infrastructure.repository.ConcertJpaRepository;
import hhplus.concert.core.concert.domain.port.ConcertQueryPort;
import hhplus.concert.core.concert.infrastructure.repository.ConcertOptionJpaRepository;
import hhplus.concert.core.concert.infrastructure.repository.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertQueryAdapter implements ConcertQueryPort {

    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertOptionJpaRepository concertOptionJpaRepository;
    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Concert> findAllConcerts() {
        return concertJpaRepository.findAll();
    }

    @Override
    public List<ConcertOption> findAllConcertOptionsByConcertId(Long concertId) {
        return concertOptionJpaRepository.findAllByConcertId(concertId);
    }

    @Override
    public Optional<ConcertOption> findConcertOptionById(Long id) {
        return concertOptionJpaRepository.findConcertOptionById(id);
    }

    @Override
    public List<Seat> findAllSeatsByConcertOptionId(Long concertOptionId) {
        return seatJpaRepository.findAllByConcertOptionId(concertOptionId);
    }

    @Override
    public List<Seat> findAllByIdsWithPessimisticWriteLock(List<Long> ids) {
        return seatJpaRepository.findAllByIdsWithPessimisticWriteLock(ids);
    }
}
