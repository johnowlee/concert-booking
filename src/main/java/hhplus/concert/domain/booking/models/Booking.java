package hhplus.concert.domain.booking.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BookingErrorCode;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatPriceByGrade;
import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hhplus.concert.domain.booking.models.BookingRule.BOOKING_EXPIRY_MINUTES;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    @OneToOne(mappedBy = "booking")
    private Payment payment;

    @Builder
    private Booking(Long id, BookingStatus bookingStatus, LocalDateTime bookingDateTime, String concertTitle, User user, List<BookingSeat> bookingSeats, Payment payment) {
        this.id = id;
        this.bookingStatus = bookingStatus;
        this.bookingDateTime = bookingDateTime;
        this.concertTitle = concertTitle;
        this.user = user;
        this.bookingSeats = bookingSeats;
        this.payment = payment;
    }

    // 예약만료시간 체크
    public void validateBookingDateTime() {
        if (isBookingDateTimeExpired()) {
            throw new RestApiException(BookingErrorCode.EXPIRED_BOOKING_TIME);
        }
    }

    public boolean isBookingDateTimeExpired() {
        return getMinutesSinceBooking() > BOOKING_EXPIRY_MINUTES.toLong();
    }

    private long getMinutesSinceBooking() {
        return Duration.between(this.getBookingDateTime(), LocalDateTime.now()).toMinutes();
    }

    public void markAsComplete() {
        this.bookingStatus = BookingStatus.COMPLETE;
    }

    public void changeSeatsBookingStatusToBooked() {
        this.bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .forEach(seat -> seat.changeBookingStatus(SeatBookingStatus.BOOKED));
    }

    public static Booking buildBooking(ConcertOption concertOption, User user) {
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.INCOMPLETE)
                .bookingDateTime(LocalDateTime.now())
                .concertTitle(concertOption.getConcert().getTitle())
                .user(user)
                .build();
        return booking;
    }

    public int getTotalPrice() {
        return this.getBookingSeats().size() * SeatPriceByGrade.A.getValue();
    }
}
