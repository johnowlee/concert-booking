package hhplus.concert.api.mock.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookingRequest(@JsonProperty("user_id") long userId,
                             @JsonProperty("concert_id") long concertId,
                             @JsonProperty("seat_no") String seats) {}
