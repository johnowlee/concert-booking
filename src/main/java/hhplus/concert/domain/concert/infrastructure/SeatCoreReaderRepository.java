package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.repositories.SeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatCoreReaderRepository implements SeatReaderRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> getSeatsByIds(List<Long> ids) {
        return seatJpaRepository.findAllByIds(ids);
    }

    @Override
    public List<Seat> getSeatsByConcertOptionId(Long concertOptionId) {
        return seatJpaRepository.findAllByConcertOptionId(concertOptionId);
    }
}
