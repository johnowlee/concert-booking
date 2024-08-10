package hhplus.concert.domain.booking.repositories;


import hhplus.concert.domain.booking.models.BookingSeat;

import java.util.List;

public interface BookingSeatReaderRepository {

    List<BookingSeat> getBookingSeatsBySeatIds(List<Long> seatIds);
}
