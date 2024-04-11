package hhplus.concert.domain.payment.model;

import hhplus.concert.domain.booking.model.BookingEntity;
import hhplus.concert.domain.user.models.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    private LocalDateTime paymentDateTime;

    private long paymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private BookingEntity bookingEntity;
}
