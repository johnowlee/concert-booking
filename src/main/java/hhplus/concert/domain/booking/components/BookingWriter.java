package hhplus.concert.domain.booking.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.repositories.BookingWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingWriter {

    private final BookingWriterRepository bookingWriterRepository;

    public Booking saveBooking(Booking booking) {
        return bookingWriterRepository.save(booking);
    }
}
