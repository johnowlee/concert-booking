package hhplus.concert.core.booking.infrastructure.adapter;

import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;
import hhplus.concert.core.booking.infrastructure.repository.BookingJpaRepository;
import hhplus.concert.core.booking.domain.port.BookingCommandPort;
import hhplus.concert.core.booking.infrastructure.repository.BookingSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingCommandAdapter implements BookingCommandPort {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingSeatJpaRepository bookingSeatJpaRepository;

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingJpaRepository.save(booking);
    }

    @Override
    public List<BookingSeat> saveAllBookingSeats(List<BookingSeat> bookingSeats) {
        return bookingSeatJpaRepository.saveAll(bookingSeats);
    }
}
