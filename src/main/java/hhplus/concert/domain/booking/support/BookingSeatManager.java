package hhplus.concert.domain.booking.support;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookingSeatManager {

    public List<BookingSeat> createBookingSeats(List<Seat> seats, Booking booking) {
        return seats.stream()
                .map(s -> BookingSeat.createBookingSeat(booking, s))
                .collect(Collectors.toList());
    }
}
