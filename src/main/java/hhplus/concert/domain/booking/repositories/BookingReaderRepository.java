package hhplus.concert.domain.booking.repositories;


import hhplus.concert.domain.booking.models.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingReaderRepository {

    List<Booking> getBookingsByUserId(Long userId);

    Optional<Booking> getBookingById(Long id);

    List<Booking> getBookingsBySeatIds(List<Long> ids);
}
