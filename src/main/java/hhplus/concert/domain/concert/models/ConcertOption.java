package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    private ConcertOption(Long id, LocalDateTime concertDateTime, String place, List<Seat> seats, Concert concert) {
        this.id = id;
        this.concertDateTime = concertDateTime;
        this.place = place;
        this.seats = seats;
        this.concert = concert;
    }
}
