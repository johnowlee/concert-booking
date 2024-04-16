package hhplus.concert.entities.concert;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
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

    public ConcertOption toConcertOption() {
        return ConcertOption.builder()
                .id(id)
                .concertDateTime(concertDateTime)
                .place(place)
                .seats(getSeats())
                .build();
    }

    private List<Seat> getSeats() {
        return seatEntities.stream()
                .map(se -> se.toSeat())
                .collect(Collectors.toList());
    }

    public ConcertOption toConcertOptionWithConcert() {
        return ConcertOption.builder()
                .id(id)
                .concertDateTime(concertDateTime)
                .place(place)
                .concert(concertEntity.toConcert())
                .seats(getSeats())
                .build();
    }

}
