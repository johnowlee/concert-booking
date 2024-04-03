package hhplus.concert.domain.model.concert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Concert {

    @Id
    @GeneratedValue
    @Column(name = "concert_id")
    private long id;

    private String title;
    private LocalDateTime concertDatetime;
}
