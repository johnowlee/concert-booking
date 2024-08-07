package hhplus.concert.domain.concert.models;

public enum SeatGrade {
    A(100000), B(50000), C(30000);

    int price;

    private SeatGrade(int price) {
        this.price = price;
    }

    public int getValue() {
        return price;
    }
}
