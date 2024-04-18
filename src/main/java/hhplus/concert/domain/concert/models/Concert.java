package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "concert")
public class Concert {

    @Id
    @GeneratedValue
    @Column(name = "concert_id")
    private Long id;

    private String title;
    private String organizer;

    @OneToMany(mappedBy = "concert")
    private List<ConcertOption> concertOptions = new ArrayList<>();
}
