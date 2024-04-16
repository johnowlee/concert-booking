package hhplus.concert.api.concert.dto.response;

import hhplus.concert.domain.concert.models.Concert;

import java.util.List;

public record ConcertResponse(Concert concert) {
    public static ConcertResponse from(Concert concert) {
        return new ConcertResponse(concert);
    }
}
