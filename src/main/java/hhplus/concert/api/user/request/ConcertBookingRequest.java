package hhplus.concert.api.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import hhplus.concert.api.concert.controller.request.ValidSeatIds;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record ConcertBookingRequest(
        @ValidSeatIds
        @JsonProperty("seat_id") String seatId
) {

    public List<Long> seatIds() {
        return Arrays.stream(seatId.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
    }
}
