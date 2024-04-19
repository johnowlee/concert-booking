package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.repositories.BookingReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookingCoreReaderRepository implements BookingReaderRepository {

    private final BookingJpaRepository bookingJpaRepository;

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingJpaRepository.findBookingsByUserId(userId);

    }

    @Override
    public Booking getBookingsByBookingId(Long bookingId) {
//        return bookingJpaRepository.findById(bookingId)
//                .orElseThrow(NoSuchElementException::new)
//                .toBooking();
        return null;
    }
}
