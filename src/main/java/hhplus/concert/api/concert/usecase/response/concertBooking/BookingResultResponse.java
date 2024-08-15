package hhplus.concert.api.concert.usecase.response.concertBooking;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BookingResultResponse(
        LocalDateTime bookingDateTime,
        String bookerName,
        String concertTitle,
        LocalDateTime concertDateTime,
        List<String> seats) {

    public static BookingResultResponse of(User user, Booking booking, List<Seat> seats) {
        return new BookingResultResponse(
                booking.getBookingDateTime(),
                user.getName(),
                booking.getConcertTitle(),
                seats.get(0).getConcertOption().getConcertDateTime(),
                getSeatNos(seats)
        );
    }

    private static List<String> getSeatNos(List<Seat> seats) {
        return seats.stream()
                .map(s -> s.getSeatNo())
                .collect(Collectors.toList());
    }
}
