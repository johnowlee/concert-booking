package hhplus.concert.domain.model.concert;

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
