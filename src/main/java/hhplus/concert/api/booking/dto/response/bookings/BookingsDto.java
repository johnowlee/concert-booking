package hhplus.concert.api.booking.dto.response.bookings;



import hhplus.concert.domain.booking.models.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BookingsDto(Long bookingId,
                          BookingStatus bookingStatus,
                          LocalDateTime bookingDateTime,
                          String title,
                          LocalDateTime concertDateTime,
                          List<String> seatNo) {
    public static BookingsDto of(Long bookingId,
                                 BookingStatus bookingStatus,
                                 LocalDateTime bookingDateTime,
                                 String title,
                                 LocalDateTime concertDateTime,
                                 List<String> seatNo) {
        return new BookingsDto(bookingId, bookingStatus, bookingDateTime, title, concertDateTime, seatNo);
    }
}
