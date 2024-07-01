package hhplus.concert.domain.booking.models;

public enum BookingRule {
    BOOKING_EXPIRY_MINUTES(5);

    int value;

    private BookingRule(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public long toLong() {
        return Long.parseLong(String.valueOf(this.getValue()));
    }
}
