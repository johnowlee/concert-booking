package hhplus.concert.domain.booking.models;

public enum BookingManager {
    BOOKING_EXPIRY_MINUTES(5);

    int value;

    private BookingManager(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public long toLong() {
        return Long.parseLong(String.valueOf(this.getValue()));
    }
}
