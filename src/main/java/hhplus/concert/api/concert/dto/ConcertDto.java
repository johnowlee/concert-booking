package hhplus.concert.api.concert.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ConcertDto {
    private Long concertId;
    private String title;
    private String organizer;
    private List<ConcertOptionDto> concertOptions;

    @Builder
    private ConcertDto(Long concertId, String title, String organizer, List<ConcertOptionDto> concertOptions) {
        this.concertId = concertId;
        this.title = title;
        this.organizer = organizer;
        this.concertOptions = concertOptions;
    }
}
