package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    private Concert(Long id, String title, String organizer) {
        this.id = id;
        this.title = title;
        this.organizer = organizer;
    }
}
