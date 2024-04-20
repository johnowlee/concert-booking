package hhplus.concert.api.booking.dto.response.booking;

import hhplus.concert.domain.booking.models.Booking;

public record BookingResponse(BookingDto booking, BookingUserDto bookingUser, BookingConcertDto bookingConcert) {
    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                BookingDto.from(booking),
                BookingUserDto.from(booking.getUser()),
                BookingConcertDto.from(booking)
        );
    }


}
