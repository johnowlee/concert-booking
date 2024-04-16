package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.repositories.BookingReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookingCoreReaderRepository implements BookingReaderRepository {

    private final BookingJpaRepository bookingJpaRepository;

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingJpaRepository.findBookingsByUserId(userId).stream()
                .map(be -> be.toBooking())
                .collect(Collectors.toList());
    }
}
