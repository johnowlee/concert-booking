package hhplus.concert.api.fakeStore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookingRequest(@JsonProperty("seat_no") String seats) {}
