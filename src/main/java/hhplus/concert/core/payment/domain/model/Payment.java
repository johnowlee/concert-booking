package hhplus.concert.core.payment.domain.model;

import hhplus.concert.core.booking.domain.model.Booking;
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
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
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
