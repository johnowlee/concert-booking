package hhplus.concert.domain.booking.model;

import hhplus.concert.domain.concert.model.Seat;
import jakarta.persistence.*;

@Entity
public class BookingSeat {

    @Id
    @GeneratedValue
    @Column(name = "booking_seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
