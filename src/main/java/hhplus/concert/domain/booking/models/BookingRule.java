package hhplus.concert.domain.booking.models;

public enum BookingRule {
    BOOKING_EXPIRY_MINUTES(5);

    long minutes;

    private BookingRule(int minutes) {
        this.minutes = minutes;
    }

    public long getMinutes() {
        return minutes;
    }
}
