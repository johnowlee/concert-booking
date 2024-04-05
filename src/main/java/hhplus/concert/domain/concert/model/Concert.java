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
    private LocalDateTime concertDateTime;

    @OneToMany(mappedBy = "concert")
    private List<Seat> seats = new ArrayList<>();
}
