package hhplus.concert.domain.history.balance.models;

import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.support.ClockManager;
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

    public static Balance of(long amount, TransactionType transactionType, User user, LocalDateTime transactionDateTime) {
        return builder()
                .amount(amount)
                .transactionType(transactionType)
                .transactionDateTime(transactionDateTime)
                .user(user)
                .build();
    }

    public static Balance createChargeBalance(User user, long amount, ClockManager clockManager) {
        return of(amount, TransactionType.CHARGE, user, clockManager.getNowDateTime());
    }

    public static Balance createUseBalanceFrom(Payment payment) {
        return of(payment.getBooking().getTotalPrice(), TransactionType.USE, payment.getUser(), payment.getPaymentDateTime());
    }
}
