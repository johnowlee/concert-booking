package hhplus.concert.api.concert.dto.response.concertOptions;

import hhplus.concert.domain.concert.models.ConcertOption;

import java.time.LocalDateTime;

public record ConcertOptionDto(Long concertOptionId, String place, LocalDateTime dateTime) {
    public static ConcertOptionDto from(ConcertOption concertOption) {
        return new ConcertOptionDto(concertOption.getId(), concertOption.getPlace(), concertOption.getConcertDateTime());
    }
}
