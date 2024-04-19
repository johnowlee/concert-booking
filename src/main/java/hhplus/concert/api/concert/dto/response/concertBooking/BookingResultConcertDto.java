package hhplus.concert.api.concert.dto.response.concertBooking;

import java.time.LocalDateTime;
import java.util.List;

public record BookingResultConcertDto(String title, LocalDateTime concertDateTime, List<String> seats) {
    public static BookingResultConcertDto of(String title, LocalDateTime concertDateTime, List<String> seats) {
        return new BookingResultConcertDto(title, concertDateTime, seats);
    }
}
