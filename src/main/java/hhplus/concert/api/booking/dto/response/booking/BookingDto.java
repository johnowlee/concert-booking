package hhplus.concert.api.booking.dto.response.booking;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.models.BookingStatus;

import java.time.LocalDateTime;

public record BookingDto(Long bookingId, BookingStatus bookingStatus, LocalDateTime bookingDateTime) {
    public static BookingDto from(Booking booking) {
        return new BookingDto(booking.getId(), booking.getBookingStatus(), booking.getBookingDateTime());
    }
}
