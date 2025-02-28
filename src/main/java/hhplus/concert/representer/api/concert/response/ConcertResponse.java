package hhplus.concert.representer.api.concert.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record ConcertResponse(Long id, String title, String organizer) {

}
