package hhplus.concert.core.booking.infrastructure.adapter;

import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;
import hhplus.concert.core.booking.infrastructure.repository.BookingJpaRepository;
import hhplus.concert.core.booking.domain.port.BookingQueryPort;
import hhplus.concert.core.booking.infrastructure.repository.BookingSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingQueryAdapter implements BookingQueryPort {

    private final BookingJpaRepository bookingJpaRepository;
    private final BookingSeatJpaRepository bookingSeatJpaRepository;

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingJpaRepository.findBookingsByUserId(userId);

    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingJpaRepository.findById(id);
    }

    @Override
    public List<BookingSeat> getBookingSeatsBySeatIds(List<Long> seatIds) {
        return bookingSeatJpaRepository.findAllBySeatIds(seatIds);
    }
}
