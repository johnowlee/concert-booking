package hhplus.concert.api.booking.dto.response.bookings;

import hhplus.concert.domain.booking.models.Booking;

import java.util.List;
import java.util.stream.Collectors;

public record BookingsResponse(List<BookingsDto> bookings) {

    public static BookingsResponse from(List<Booking> bookings) {
        return new BookingsResponse(convertBookingsDtoList(bookings));
    }

    private static List<BookingsDto> convertBookingsDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(b -> toBookingsDto(b))
                .collect(Collectors.toList());
    }

    private static BookingsDto toBookingsDto(Booking booking) {
        return BookingsDto.of(booking.getId(),
                booking.getBookingStatus(),
                booking.getBookingDateTime(),
                booking.getConcertTitle());
    }
}
