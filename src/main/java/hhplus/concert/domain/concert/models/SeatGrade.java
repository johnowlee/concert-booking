package hhplus.concert.domain.concert.models;

public enum SeatGrade {
    A(30000), B(15000);

    int price;

    private SeatGrade(int price) {
        this.price = price;
    }

    public int getValue() {
        return price;
    }
}
