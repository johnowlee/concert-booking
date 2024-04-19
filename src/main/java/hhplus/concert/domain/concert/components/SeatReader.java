package hhplus.concert.domain.concert.components;

import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.repositories.SeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatReader {

    private final SeatReaderRepository seatReaderRepository;
    public List<Seat> getSeatsByIds(List<Long> ids) {
        return seatReaderRepository.getSeatsByIds(ids);
    }
}
