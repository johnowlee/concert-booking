package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingSeatWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingSeatCoreWriterRepository implements BookingSeatWriterRepository {

    private final BookingSeatJpaRepository bookingSeatJpaRepository;

    @Override
    public List<BookingSeat> saveAll(List<BookingSeat> bookingSeats) {
        return bookingSeatJpaRepository.saveAll(bookingSeats);
    }
}
