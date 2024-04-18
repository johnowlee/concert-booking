package hhplus.concert.api.concert.dto.response.concerts;

import java.time.LocalDateTime;

public record ConcertOptionDto(Long concertOptioinId, String place, LocalDateTime dateTime, int availableSeatsCount
                               ) {
    public static ConcertOptionDto of(Long concertOptioinId, String place, LocalDateTime dateTime, int availableSeatsCount) {
        return new ConcertOptionDto(concertOptioinId, place, dateTime, availableSeatsCount);
    }
}
