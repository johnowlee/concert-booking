package hhplus.concert.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.concert.models.Concert;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ConcertResponse(
        Long id,

        String title,
        String organizer
) {

    public static ConcertResponse from(Concert concert) {
        return new ConcertResponse(concert.getId(), concert.getTitle(), concert.getOrganizer());
    }
}
