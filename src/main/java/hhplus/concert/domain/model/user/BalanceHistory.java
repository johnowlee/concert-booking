package hhplus.concert.domain.model.user;

import hhplus.concert.domain.model.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BalanceHistory {

    @Id
    @GeneratedValue
    @Column(name = "balance_history_id")
    private long id;

    private long amount;
    private TransactionType transactionType;
    private LocalDateTime transactionDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
