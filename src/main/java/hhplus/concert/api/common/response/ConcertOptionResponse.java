package hhplus.concert.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ConcertOptionResponse(
        Long id,
        LocalDateTime concertDateTime,
        String place,
        Concert concert
) {
    public static ConcertOptionResponse from(ConcertOption concertOption) {
        return new ConcertOptionResponse(concertOption.getId(), concertOption.getConcertDateTime(), concertOption.getPlace(), null);
    }
}
