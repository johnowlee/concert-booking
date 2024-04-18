package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "concert_option")
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
