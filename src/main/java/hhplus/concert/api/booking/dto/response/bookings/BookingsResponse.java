package hhplus.concert.api.booking.dto.response.bookings;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.Seat;

import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.api.booking.dto.response.BookingCommonUtil.*;

public record BookingsResponse(List<BookingsDto> bookings) {

    public static BookingsResponse from(List<Booking> bookings) {
        return new BookingsResponse(setBookingDtos(bookings));
    }

    private static List<BookingsDto> setBookingDtos(List<Booking> bookings) {
        return bookings.stream()
                .map(b -> {
                    List<Seat> seats = getSeatsFromBooking(b);
                    return BookingsDto.of(
                            b.getId(),
                            b.getBookingStatus(),
                            b.getBookingDateTime(),
                            b.getConcertTitle(),
                            getConcertOptionFromSeats(seats).getConcertDateTime(),
                            getSeatNoFromSeats(seats)
                    );
                })
                .collect(Collectors.toList());
    }


}
