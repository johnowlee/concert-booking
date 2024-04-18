package hhplus.concert.api.concert.dto.response.concerts;

import java.util.List;

public record ConcertDto(Long concertId,
                         String title,
                         String organizer,
                         List<ConcertOptionDto> concertOptions) {
    public static ConcertDto of(Long concertId,
                                  String title,
                                  String organizer,
                                  List<ConcertOptionDto> concertOptions) {
        return new ConcertDto(concertId, title, organizer, concertOptions);
    }
}
