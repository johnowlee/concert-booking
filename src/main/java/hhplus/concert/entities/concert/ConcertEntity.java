package hhplus.concert.entities.concert;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Concert toConcert() {
        return Concert.builder()
                .id(id)
                .title(title)
                .organizer(organizer)
                .concertOptions(getConcertOptions())
                .build();
    }

    private List<ConcertOption> getConcertOptions() {
        return concertOptionEntities.stream()
                .map(coe -> coe.toConcertOption())
                .collect(Collectors.toList());
    }
}
