package hhplus.concert.api.booking.dto.response.booking;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BookingConcertDto(Long concertOptionId,
                                String title,
                                LocalDateTime concertDateTime,
                                List<BookingSeatDto> seats) {

    public static BookingConcertDto from(Booking booking) {
        List<Seat> seats = getSeatsFromBooking(booking);
        ConcertOption concertOption = getConcertOptionFromSeats(seats);

        return new BookingConcertDto(
                concertOption.getId(),
                booking.getConcertTitle(),
                concertOption.getConcertDateTime(),
                toBookingSeatDtos(seats)
        );
    }

    private static List<BookingSeatDto> toBookingSeatDtos(List<Seat> seats) {
        return seats.stream()
                .map(BookingSeatDto::from)
                .collect(Collectors.toList());
    }

    private static List<Seat> getSeatsFromBooking(Booking booking) {
        return booking.getBookingSeats().stream()
                .map(BookingSeat::getSeat)
                .collect(Collectors.toList());
    }

    private static ConcertOption getConcertOptionFromSeats(List<Seat> seats) {
        return seats.get(0).getConcertOption();
    }
}
