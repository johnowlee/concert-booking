package hhplus.concert.domain.balance.models;

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
@Table(name = "balance_history")
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

    @Builder
    private BalanceHistory(long amount, TransactionType transactionType, LocalDateTime transactionDateTime, User user) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDateTime = transactionDateTime;
        this.user = user;
    }

    public static BalanceHistory createBalanceHistory(User user, long amount, TransactionType transactionType) {
        return builder()
                .amount(amount)
                .transactionType(transactionType)
                .transactionDateTime(LocalDateTime.now())
                .user(user)
                .build();
    }
}
