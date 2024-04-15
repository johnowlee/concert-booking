package hhplus.concert.entities.concert;

import hhplus.concert.entities.booking.BookingSeatEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seat")
public class SeatEntity {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    private String seatNo;

    @OneToMany(mappedBy = "seatEntity")
    private List<BookingSeatEntity> bookingSeatEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_option_id")
    private ConcertOptionEntity concertOptionEntity;


}
