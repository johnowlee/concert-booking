package hhplus.concert.domain.concert.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ConcertOption {

    private Long id;
    private LocalDateTime concertDateTime;
    private String place;
    private List<Seat> seats;

    @Builder
    private ConcertOption(Long id, LocalDateTime concertDateTime, String place, List<Seat> seats) {
        this.id = id;
        this.concertDateTime = concertDateTime;
        this.place = place;
        this.seats = seats;
    }
}
