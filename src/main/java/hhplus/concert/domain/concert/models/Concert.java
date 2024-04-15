package hhplus.concert.domain.concert.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Concert {

    private Long concertId;
    private String organizer;
    private String title;
}
