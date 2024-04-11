package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concert")
public class ConcertEntity {

    @Id
    @GeneratedValue
    @Column(name = "concert_id")
    private Long id;

    private String title;
    private String organizer;

    @OneToMany(mappedBy = "concertEntity")
    private List<ConcertOptionEntity> concertOptionEntities = new ArrayList<>();
}
