package hhplus.concert.core.user.domain.model;

import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.payment.domain.model.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private long balance;

    @Column(name = "versions")
    @Version
    private Long version;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Payment> paymentHistories = new ArrayList<>();

    @Builder
    private User(String name, long balance,
                 List<Booking> bookings,
                 List<Payment> paymentHistories,
                 Long version) {
        this.name = name;
        this.balance = balance;
        this.bookings = bookings;
        this.paymentHistories = paymentHistories;
        this.version = version;
    }

    public static User createUser(String name) {
        return builder()
                .name(name)
                .balance(0)
                .build();
    }

    public void chargeBalance(long amount) {
        this.balance += amount;
    }

    public void useBalance(long amount) {
        this.balance -= amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (!(object instanceof User user)) {
            return false;
        }
        return this.getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public boolean notEquals(User user) {
        return !equals(user);
    }

    public boolean isBalanceLessThan(long amount) {
        return this.balance < amount;
    }
}