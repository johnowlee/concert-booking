package hhplus.concert.domain.model.user;

import hhplus.concert.domain.model.concert.Booking;
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
