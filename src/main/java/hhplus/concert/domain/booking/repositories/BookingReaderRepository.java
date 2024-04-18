package hhplus.concert.domain.booking.repositories;


import hhplus.concert.domain.booking.models.Booking;

import java.util.List;

public interface BookingReaderRepository {

    List<Booking> getBookingsByUserId(Long userId);

    Booking getBookingsByBookingId(Long bookingId);
}
