package hhplus.concert.domain.concert.models;

public enum SeatPriceByGrade {
    A(30000), B(15000);

    int value;

    private SeatPriceByGrade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
