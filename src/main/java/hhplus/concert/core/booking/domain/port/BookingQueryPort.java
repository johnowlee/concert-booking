package hhplus.concert.core.booking.domain.port;


import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.model.BookingSeat;

import java.util.List;
import java.util.Optional;

public interface BookingQueryPort {

    List<Booking> findBookingsByUserId(Long userId);

    Optional<Booking> findBookingById(Long id);

    List<BookingSeat> findBookingSeatsBySeatIds(List<Long> seatIds);
}
