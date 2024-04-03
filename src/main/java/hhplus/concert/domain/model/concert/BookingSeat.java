package hhplus.concert.domain.model.concert;

import hhplus.concert.domain.model.user.User;
import jakarta.persistence.*;

@Entity
public class BookingSeat {

    @Id
    @GeneratedValue
    @Column(name = "booking_seat_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
