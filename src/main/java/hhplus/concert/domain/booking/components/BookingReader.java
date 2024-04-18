package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.repositories.BookingReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingReader {

    private final BookingReaderRepository bookingReaderRepository;

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingReaderRepository.getBookingsByUserId(userId);
    }

    public Booking getBookingByBookingId(Long bookingId) {
        return bookingReaderRepository.getBookingsByBookingId(bookingId);
    }


}
