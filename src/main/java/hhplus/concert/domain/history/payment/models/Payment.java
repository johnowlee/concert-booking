package hhplus.concert.domain.history.payment.models;

import hhplus.concert.domain.booking.models.Booking;
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
@Table(name = "payment_history")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_history_id")
    private Long id;

    private LocalDateTime paymentDateTime;

    private int paymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Builder
    private Payment(LocalDateTime paymentDateTime, int paymentAmount, User user, Booking booking) {
        this.paymentDateTime = paymentDateTime;
        this.paymentAmount = paymentAmount;
        this.user = user;
        this.booking = booking;
    }

    public static Payment of(Booking booking, User payer, LocalDateTime payTime) {
        return Payment.builder()
                .paymentDateTime(payTime)
                .paymentAmount(booking.getTotalPrice())
                .user(payer)
                .booking(booking)
                .build();
    }

    public static Payment of(Booking booking, User payer) {
        return of(booking, payer, null);
    }

    public static Payment createPayment(Booking booking, LocalDateTime paymentDateTime) {
        return of(booking, booking.getUser(), paymentDateTime);
    }
}
