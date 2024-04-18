package hhplus.concert.domain.booking.repositories;


import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;

import java.util.List;

public interface BookingWriterRepository {

    public Booking bookConcert(Booking booking);

    public List<BookingSeat> saveAllBookingSeat(List<BookingSeat> bookingSeats);

    BookingSeat saveBookingSeat(BookingSeat bookingSeat);
}
