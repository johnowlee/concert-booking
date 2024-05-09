package hhplus.concert.api.booking.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentRequest(@JsonProperty("user_id") Long userId) {
}
