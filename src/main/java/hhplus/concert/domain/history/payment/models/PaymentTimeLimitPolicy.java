package hhplus.concert.domain.history.payment.models;

import lombok.Getter;

@Getter
public enum PaymentTimeLimitPolicy {
    ALLOWED_MINUTES(5);

    final long minutes;

    private PaymentTimeLimitPolicy(int minutes) {
        this.minutes = minutes;
    }

}
