package hhplus.concert.domain.booking.model;

import hhplus.concert.domain.concert.models.SeatEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_seat")
public class BookingSeatEntity {

    @Id
    @GeneratedValue
    @Column(name = "booking_seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingEntity bookingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private SeatEntity seatEntity;
}
