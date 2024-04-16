package hhplus.concert.domain.user.models;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.queue.model.Queue;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
public class User {

    private Long id;
    private String name;
    private long balance;
    private List<Queue> queues;
    private List<BalanceHistory> balanceHistories;
    private List<Booking> bookings;
    private List<Payment> payments;

    @Builder
    private User(Long id, String name, long balance,
                @Singular List<Queue> queues,
                @Singular List<BalanceHistory> balanceHistories,
                @Singular List<Booking> bookings,
                @Singular List<Payment> payments) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.queues = queues;
        this.balanceHistories = balanceHistories;
        this.bookings = bookings;
        this.payments = payments;
    }

    public void chargeBalance(long amount) {
        if (amount <= 0 ) {
            throw new IllegalArgumentException("충전금액은 0보다 커야합니다.");
        }
        this.balance += amount;
    }

    public void useBalance(long amount) {
        if (this.balance < amount) {
            throw new IllegalStateException("잔액이 부족합니다.");
        }
        if (amount <= 0 ) {
            throw new IllegalArgumentException("사용금액은 0보다 커야합니다.");
        }
        this.balance -= amount;
    }
}
