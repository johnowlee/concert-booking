package hhplus.concert.domain.user.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
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
    private List<Payment> payments = new ArrayList<>();

    @Builder
    private User(String name, long balance,
                 List<Booking> bookings,
                 List<Payment> payments,
                 Long version) {
        this.name = name;
        this.balance = balance;
        this.bookings = bookings;
        this.payments = payments;
        this.version = version;
    }

    public void chargeBalance(long amount) {
        if (amount <= 0 ) {
            throw new RestApiException(BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT);
        }
        this.balance += amount;
    }

    public void useBalance(long amount) {
        if (this.balance < amount) {
            throw new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE);
        }
        if (amount <= 0 ) {
            throw new RestApiException(BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT);
        }
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
}