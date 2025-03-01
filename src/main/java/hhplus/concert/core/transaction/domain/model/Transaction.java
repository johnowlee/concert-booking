package hhplus.concert.core.transaction.domain.model;

import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.core.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "transaction", indexes = @Index(name = "idx_transaction", columnList = "user_id, transaction_date_time"))
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    private long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private LocalDateTime transactionDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Transaction(long amount, TransactionType transactionType, LocalDateTime transactionDateTime, User user) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDateTime = transactionDateTime;
        this.user = user;
    }

    public static Transaction of(long amount, TransactionType transactionType, User user, LocalDateTime transactionDateTime) {
        return builder()
                .amount(amount)
                .transactionType(transactionType)
                .transactionDateTime(transactionDateTime)
                .user(user)
                .build();
    }

    public static Transaction createChargeBalance(User user, long amount, LocalDateTime transactionDateTime) {
        return of(amount, TransactionType.CHARGE, user, transactionDateTime);
    }

    public static Transaction createUseBalanceFrom(Payment payment) {
        return of(payment.getBooking().getTotalPrice(), TransactionType.USE, payment.getUser(), payment.getPaymentDateTime());
    }
}
