package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.booking.repositories.BookingWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingCoreWriterRepository implements BookingWriterRepository {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingSeatJpaRepository bookingSeatJpaRepository;

    @Override
    public Booking bookConcert(Booking booking) {
        return bookingJpaRepository.save(booking);
    }

    @Override
    public List<BookingSeat> saveAllBookingSeat(List<BookingSeat> bookingSeats) {
        return bookingSeatJpaRepository.saveAll(bookingSeats);
    }
}
