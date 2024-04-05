package hhplus.concert.domain.concert.model;

import hhplus.concert.domain.booking.model.BookingSeat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Seat {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private long id;

    private String seatNo;

    @OneToMany(mappedBy = "seat")
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;


}
