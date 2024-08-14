package hhplus.concert.domain.booking.repositories;


import hhplus.concert.domain.booking.models.BookingSeat;

import java.util.List;

public interface BookingSeatWriterRepository {

    public List<BookingSeat> saveAll(List<BookingSeat> bookingSeats);
}
