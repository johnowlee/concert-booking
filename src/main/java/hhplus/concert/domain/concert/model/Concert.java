package hhplus.concert.domain.concert.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Concert {

    @Id
    @GeneratedValue
    @Column(name = "concert_id")
    private long id;

    private String title;
    private String organizer;

    @OneToMany(mappedBy = "concert")
    private List<ConcertOption> concertOptions = new ArrayList<>();
}
