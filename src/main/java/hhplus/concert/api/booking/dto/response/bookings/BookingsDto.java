package hhplus.concert.api.booking.dto.response.bookings;


import hhplus.concert.domain.booking.models.BookingStatus;

import java.time.LocalDateTime;

public record BookingsDto(Long bookingId,
                          BookingStatus bookingStatus,
                          LocalDateTime bookingDateTime,
                          String title) {
    public static BookingsDto of(Long bookingId,
                                 BookingStatus bookingStatus,
                                 LocalDateTime bookingDateTime,
                                 String title) {
        return new BookingsDto(bookingId, bookingStatus, bookingDateTime, title);
    }
}
