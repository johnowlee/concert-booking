package hhplus.concert.domain.model.concert;

import hhplus.concert.domain.model.user.User;
import hhplus.concert.domain.model.enums.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    @Column(name = "booking_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private LocalDateTime bookingDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
