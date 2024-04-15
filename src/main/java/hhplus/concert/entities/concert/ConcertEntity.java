package hhplus.concert.entities.concert;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
