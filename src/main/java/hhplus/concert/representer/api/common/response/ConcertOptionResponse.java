package hhplus.concert.representer.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.concert.models.ConcertOption;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record ConcertOptionResponse(
        Long id,
        LocalDateTime concertDateTime,
        String place,
        ConcertResponse concert
) {
    public static ConcertOptionResponse from(ConcertOption concertOption) {
        return new ConcertOptionResponse(concertOption.getId(), concertOption.getConcertDateTime(), concertOption.getPlace(), null);
    }
}
