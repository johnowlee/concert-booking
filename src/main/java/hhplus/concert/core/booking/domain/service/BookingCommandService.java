package hhplus.concert.core.booking.domain.service;

import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;
import hhplus.concert.core.booking.domain.port.BookingCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingCommandService {

    private final BookingCommandPort bookingCommandPort;

    public Booking saveBooking(Booking booking) {
        return bookingCommandPort.saveBooking(booking);
    }

    public List<BookingSeat> saveBookingSeats(List<BookingSeat> bookingSeats) {
        return bookingCommandPort.saveAllBookingSeats(bookingSeats);
    }
}
