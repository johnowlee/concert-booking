package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "concert")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long id;

    private String title;
    private String organizer;

    @OneToMany(mappedBy = "concert")
    private List<ConcertOption> concertOptions = new ArrayList<>();

    @Builder
    private Concert(Long id, String title, String organizer, List<ConcertOption> concertOptions) {
        this.id = id;
        this.title = title;
        this.organizer = organizer;
        this.concertOptions = concertOptions;
    }
}
