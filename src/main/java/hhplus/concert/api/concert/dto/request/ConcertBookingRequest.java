package hhplus.concert.api.concert.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record ConcertBookingRequest(@JsonProperty("user_id") Long userId, @JsonProperty("seat_id") String seatId) {
    public List<Long> parsedSeatIds() {
        return Arrays.stream(seatId.split(","))
                .map(s -> Long.parseLong(s))
                .collect(Collectors.toList());
    }
}
