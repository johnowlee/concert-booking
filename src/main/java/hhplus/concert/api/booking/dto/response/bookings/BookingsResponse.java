package hhplus.concert.api.booking.dto.response.bookings;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BookingsResponse(List<BookingDto> bookings) {

    public static BookingsResponse from(List<Booking> bookings) {
        return new BookingsResponse(setBookingDtos(bookings));
    }

    private static List<BookingDto> setBookingDtos(List<Booking> bookings) {
        return bookings.stream()
                .map(b -> {
                    List<Seat> seats = getSeats(b);
                    return BookingDto.of(
                            b.getId(),
                            b.getBookingStatus(),
                            b.getBookingDateTime(),
                            b.getConcertTitle(),
                            getConcertDateTime(seats),
                            getSeatNo(seats)
                    );
                })
                .collect(Collectors.toList());
    }

    private static LocalDateTime getConcertDateTime(List<Seat> seats) {
        return seats.get(0).getConcertOption().getConcertDateTime();
    }

    private static List<String> getSeatNo(List<Seat> seats) {
        return seats.stream()
                .map(s -> s.getSeatNo())
                .collect(Collectors.toList());
    }

    private static List<Seat> getSeats(Booking booking) {
        return booking.getBookingSeats().stream()
                .map(bs -> bs.getSeat())
                .collect(Collectors.toList());
    }
}
