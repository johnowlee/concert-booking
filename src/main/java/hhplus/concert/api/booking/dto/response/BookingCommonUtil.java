package hhplus.concert.api.booking.dto.response;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BookingCommonUtil {
    public static ConcertOption getConcertOptionFromSeats(List<Seat> seats) {
        return seats.get(0).getConcertOption();
    }

    public static List<Seat> getSeatsFromBooking(Booking booking) {
        return booking.getBookingSeats().stream()
                .map(bs -> bs.getSeat())
                .collect(Collectors.toList());
    }
}
