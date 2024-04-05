package hhplus.concert.api.mock.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookingRequest(@JsonProperty("seat_no") String seats) {}
