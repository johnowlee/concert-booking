package hhplus.concert.api.booking.dto.response.booking;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.api.booking.dto.response.BookingCommonUtil.getConcertOptionFromSeats;
import static hhplus.concert.api.booking.dto.response.BookingCommonUtil.getSeatsFromBooking;

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
                .map(s -> BookingSeatDto.from(s))
                .collect(Collectors.toList());
    }
}
