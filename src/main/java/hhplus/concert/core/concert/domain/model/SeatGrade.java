package hhplus.concert.core.concert.domain.model;

public enum SeatGrade {
    A(100000), B(50000), C(30000);

    int price;

    private SeatGrade(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
