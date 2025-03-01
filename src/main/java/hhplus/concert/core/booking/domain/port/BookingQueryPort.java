package hhplus.concert.core.booking.domain.port;


import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;

import java.util.List;
import java.util.Optional;

public interface BookingQueryPort {

    List<Booking> getBookingsByUserId(Long userId);

    Optional<Booking> getBookingById(Long id);

    List<BookingSeat> getBookingSeatsBySeatIds(List<Long> seatIds);
}
