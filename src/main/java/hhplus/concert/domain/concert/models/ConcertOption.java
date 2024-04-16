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
    private Concert concert;
    private List<Seat> seats;

    @Builder
    private ConcertOption(Long id, LocalDateTime concertDateTime, String place, Concert concert, List<Seat> seats) {
        this.id = id;
        this.concertDateTime = concertDateTime;
        this.place = place;
        this.concert = concert;
        this.seats = seats;
    }
}
