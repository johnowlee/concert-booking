package hhplus.concert.domain.concert.support;

import hhplus.concert.domain.concert.models.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeatManager {
    public void markAllSeatsAsProcessing(List<Seat> seats) {
        seats.forEach(s -> s.markAsProcessing());
    }

    public String getConcertTitleFrom(List<Seat> seats) {
        return seats.get(0).getConcertOption().getConcert().getTitle();
    }
}
