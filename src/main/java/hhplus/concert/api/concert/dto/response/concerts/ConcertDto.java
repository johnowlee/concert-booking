package hhplus.concert.api.concert.dto.response.concerts;

import hhplus.concert.domain.concert.models.Concert;

public record ConcertDto(Long concertId,
                         String title,
                         String organizer) {
    public static ConcertDto from(Concert concert) {
        return new ConcertDto(concert.getId(), concert.getTitle(), concert.getOrganizer());
    }
}
