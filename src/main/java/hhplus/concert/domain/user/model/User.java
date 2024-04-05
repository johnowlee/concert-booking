package hhplus.concert.domain.user.model;

import hhplus.concert.domain.balance.model.BalanceHistory;
import hhplus.concert.domain.booking.model.Booking;
import hhplus.concert.domain.queue.model.Queue;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private long id;

    private String name;
    private long balance;

    @OneToMany(mappedBy = "user")
    private List<Queue> queue = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BalanceHistory> balanceHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings = new ArrayList<>();
}
