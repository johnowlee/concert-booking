package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingSeatReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingSeatCoreReaderRepository implements BookingSeatReaderRepository {

    private final BookingSeatJpaRepository bookingSeatJpaRepository;

    @Override
    public List<BookingSeat> getBookingSeatsBySeatIds(List<Long> seatIds) {
        return bookingSeatJpaRepository.findAllBySeatIds(seatIds);
    }
}
