package hhplus.concert.entities.balance;

import hhplus.concert.entities.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "balance_history")
public class BalanceHistoryEntity {

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
    private UserEntity userEntity;
}
