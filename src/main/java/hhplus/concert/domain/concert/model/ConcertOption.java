package hhplus.concert.domain.concert.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConcertOption {

    @Id
    @GeneratedValue
    @Column(name = "concert_option_id")
    private Long id;

    private LocalDateTime concertDateTime;
    private String place;

    @OneToMany(mappedBy = "concertOption")
    private List<Seat> seats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;
}
