package hhplus.concert.representer.api.concert.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record ConcertOptionResponse(Long id, LocalDateTime concertDateTime, String place) {

}
