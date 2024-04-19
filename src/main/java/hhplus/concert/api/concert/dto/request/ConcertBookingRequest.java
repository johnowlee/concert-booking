package hhplus.concert.api.concert.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ConcertBookingRequest(Long concertOptionId, @JsonProperty("seat_id") String seatId) {
    public static ConcertBookingRequest of(Long concertOptionId, String seatId) {
        return new ConcertBookingRequest(concertOptionId, seatId);
    }
}
