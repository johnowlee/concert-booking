package hhplus.concert.domain.user.models;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.queue.model.Queue;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private long balance;

    @Column(name = "versions")
    @Version
    private Long version;

    @OneToMany(mappedBy = "user")
    private List<Queue> queue = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BalanceHistory> balanceHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    @Builder
    private User(Long id, String name, long balance,
                 List<Queue> queue,
                 List<BalanceHistory> balanceHistories,
                 List<Booking> bookings,
                 List<Payment> payments) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.queue = queue;
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