package hhplus.concert.domain.user.models;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.booking.model.Booking;
import hhplus.concert.domain.payment.model.Payment;
import hhplus.concert.domain.queue.model.Queue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private long balance;

    @OneToMany(mappedBy = "user")
    private List<Queue> queue = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BalanceHistory> balanceHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();

    public static User createUser(String name, long balance) {
        User user = new User();
        user.setName(name);
        user.setBalance(balance);
        return user;
    }
}
