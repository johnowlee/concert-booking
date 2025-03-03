package hhplus.concert.core.concert.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "concert_option")
public class ConcertOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_option_id")
    private Long id;

    private LocalDateTime concertDateTime;
    private String place;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @Builder
    private ConcertOption(Long id, LocalDateTime concertDateTime, String place, Concert concert) {
        this.concertDateTime = concertDateTime;
        this.place = place;
        this.concert = concert;
    }
}
