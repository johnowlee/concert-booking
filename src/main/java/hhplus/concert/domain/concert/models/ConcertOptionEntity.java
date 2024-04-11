package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concert_option")
public class ConcertOptionEntity {

    @Id
    @GeneratedValue
    @Column(name = "concert_option_id")
    private Long id;

    private LocalDateTime concertDateTime;
    private String place;

    @OneToMany(mappedBy = "concertOptionEntity")
    private List<SeatEntity> seatEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private ConcertEntity concertEntity;
}
