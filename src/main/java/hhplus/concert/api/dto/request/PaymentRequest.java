package hhplus.concert.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentRequest(@JsonProperty("user_id") long userId,
                             @JsonProperty("booking_id") long bookingId) {}
