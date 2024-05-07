package hhplus.concert.domain.payment.models;

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
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    private LocalDateTime paymentDateTime;

    private long paymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Builder
    private Payment(LocalDateTime paymentDateTime, long paymentAmount, User user, Booking booking) {
        this.paymentDateTime = paymentDateTime;
        this.paymentAmount = paymentAmount;
        this.user = user;
        this.booking = booking;
    }

    public static Payment createBookingPayment(Booking booking, long amount) {
        return Payment.builder()
                .paymentDateTime(LocalDateTime.now())
                .paymentAmount(amount)
                .user(booking.getUser())
                .booking(booking)
                .build();
    }
}
