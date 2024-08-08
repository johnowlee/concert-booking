package hhplus.concert.domain.history.balance.models;

import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "balance_history", indexes = @Index(name = "idx_balance_history", columnList = "user_id, transaction_date_time"))
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_history_id")
    private Long id;

    private long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime transactionDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Balance(long amount, TransactionType transactionType, LocalDateTime transactionDateTime, User user) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDateTime = transactionDateTime;
        this.user = user;
    }

    public static Balance createBalanceChargeHistory(User user, long amount) {
        return builder()
                .amount(amount)
                .transactionType(TransactionType.CHARGE)
                .transactionDateTime(LocalDateTime.now())
                .user(user)
                .build();
    }

    public static Balance createBalanceUseHistory(User user, long amount) {
        return builder()
                .amount(amount)
                .transactionType(TransactionType.USE)
                .transactionDateTime(LocalDateTime.now())
                .user(user)
                .build();
    }
}
