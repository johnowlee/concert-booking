package hhplus.concert.domain.booking.models;

import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "booking")
public class Booking {

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
    private User user;

    @OneToMany(mappedBy = "booking")
    private List<BookingSeat> bookingSeatEntities = new ArrayList<>();

    @OneToOne(mappedBy = "booking")
    private Payment payment;

    public Booking toBooking() {
        return Booking.builder()
                .id(id)
                .bookingStatus(bookingStatus)
                .bookingDateTime(bookingDateTime)
                .concertTitle(concertTitle)
//                .user(user.toUser())
                .build();
    }

    public static Booking toBookingEntity(Booking booking) {
        return builder()
                .bookingStatus(BookingStatus.INCOMPLETE)
                .bookingDateTime(booking.getBookingDateTime())
                .concertTitle(booking.getConcertTitle())
//                .userEntity(User.toUserEntity(booking.getUser()))
                .build();
    }

    @Builder
    private Booking(Long id, BookingStatus bookingStatus, LocalDateTime bookingDateTime, String concertTitle, User user, List<BookingSeat> bookingSeatEntities, Payment payment) {
        this.id = id;
        this.bookingStatus = bookingStatus;
        this.bookingDateTime = bookingDateTime;
        this.concertTitle = concertTitle;
        this.user = user;
        this.bookingSeatEntities = bookingSeatEntities;
        this.payment = payment;
    }
}
