package hhplus.concert.domain.model.concert;

import jakarta.persistence.*;

@Entity
public class Seat {

    @EmbeddedId
    private SeatId seatId;

    @OneToOne(mappedBy = "seat")
    private Booking booking;
}
