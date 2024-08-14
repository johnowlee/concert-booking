package hhplus.concert.domain.booking.repositories;


import hhplus.concert.domain.booking.models.Booking;

public interface BookingWriterRepository {

    public Booking save(Booking booking);
}
