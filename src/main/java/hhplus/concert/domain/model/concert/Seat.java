package hhplus.concert.domain.model.concert;

import jakarta.persistence.*;

@Entity
public class Seat {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private long id;

    private String seatNo;

    @OneToOne(mappedBy = "seat")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;
}
