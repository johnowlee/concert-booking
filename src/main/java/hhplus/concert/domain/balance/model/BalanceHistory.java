package hhplus.concert.domain.balance.model;

import hhplus.concert.domain.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BalanceHistory {

    @Id
    @GeneratedValue
    @Column(name = "balance_history_id")
    private long id;

    private long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime transactionDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
