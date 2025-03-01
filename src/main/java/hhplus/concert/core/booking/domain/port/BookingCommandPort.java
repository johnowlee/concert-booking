package hhplus.concert.core.booking.domain.port;


import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;

import java.util.List;

public interface BookingCommandPort {

    Booking saveBooking(Booking booking);

    List<BookingSeat> saveAllBookingSeats(List<BookingSeat> bookingSeats);
}
