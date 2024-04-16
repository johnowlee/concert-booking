package hhplus.concert.api.concert.dto.response;

import hhplus.concert.api.concert.dto.SeatDto;

import java.time.LocalDateTime;
import java.util.List;

public record ConcertOptionResponse(String title,
                                    LocalDateTime concertDateTime,
                                    String organizer,
                                    List<SeatDto> seats
                                    ) {
    public static ConcertOptionResponse from(String title,
                                             LocalDateTime concertDateTime,
                                             String organizer,
                                             List<SeatDto> seats) {
        return new ConcertOptionResponse(title, concertDateTime, organizer, seats);
    }
}
