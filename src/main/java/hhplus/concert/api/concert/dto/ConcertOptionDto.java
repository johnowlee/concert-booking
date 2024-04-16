package hhplus.concert.api.concert.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConcertOptionDto {
    private Long concertOptionId;
    private String place;
    private LocalDateTime dateTime;
    private int availableSeatsCount;

    @Builder
    private ConcertOptionDto(Long concertOptionId, String place, LocalDateTime dateTime, int availableSeatsCount) {
        this.concertOptionId = concertOptionId;
        this.place = place;
        this.dateTime = dateTime;
        this.availableSeatsCount = availableSeatsCount;
    }
}
