package hhplus.concert.domain.booking.model;

import hhplus.concert.domain.payment.model.PaymentEntity;
import hhplus.concert.domain.user.models.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue
    @Column(name = "booking_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private LocalDateTime bookingDateTime;
    private String concertTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "bookingEntity")
    private List<BookingSeatEntity> bookingSeatEntities = new ArrayList<>();

    @OneToOne(mappedBy = "bookingEntity")
    private PaymentEntity paymentEntity;
}
