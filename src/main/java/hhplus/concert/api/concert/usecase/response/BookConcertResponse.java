package hhplus.concert.api.concert.usecase.response;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingSeat;
import hhplus.concert.domain.concert.models.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BookConcertResponse(
        Long bookingId,
        LocalDateTime bookingDateTime,
        String bookerName,
        String concertTitle,
        LocalDateTime concertDateTime,
        List<String> seats) {

    public static BookConcertResponse from(Booking booking) {
        return new BookConcertResponse(
                booking.getId(),
                booking.getBookingDateTime(),
                booking.getUser().getName(),
                booking.getConcertTitle(),
                getConcertDateTime(booking),
                getBookingSeatNumbers(booking)
        );
    }

    private static List<String> getBookingSeatNumbers(Booking booking) {
        List<BookingSeat> bookingSeats = booking.getBookingSeats();
        return bookingSeats.stream()
                .map(bs -> bs.getSeat().getSeatNo())
                .toList();
    }

    private static LocalDateTime getConcertDateTime(Booking booking) {
        return booking.getBookingSeats().get(0).getSeat().getConcertOption().getConcertDateTime();
    }


    private static List<String> getSeatNos(List<Seat> seats) {
        return seats.stream()
                .map(s -> s.getSeatNo())
                .collect(Collectors.toList());
    }
}
