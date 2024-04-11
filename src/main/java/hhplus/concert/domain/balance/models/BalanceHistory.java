package hhplus.concert.domain.balance.models;

import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BalanceHistory {

    @Id
    @GeneratedValue
    @Column(name = "balance_history_id")
    private Long id;

    private long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime transactionDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
