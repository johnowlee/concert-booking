package hhplus.concert.domain.booking.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hhplus.concert.api.exception.code.BookingErrorCode.*;
import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;
import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static hhplus.concert.domain.booking.models.BookingStatus.INCOMPLETE;

@Entity
@Getter
@Slf4j
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

    @Builder
    private Booking(Long id, BookingStatus bookingStatus, LocalDateTime bookingDateTime, String concertTitle, User user) {
        this.id = id;
        this.bookingStatus = bookingStatus;
        this.bookingDateTime = bookingDateTime;
        this.concertTitle = concertTitle;
        this.user = user;
    }

    public static Booking createBooking(String concertTitle, LocalDateTime bookingDateTime, User user) {
        return Booking.builder()
                .bookingStatus(INCOMPLETE)
                .bookingDateTime(bookingDateTime)
                .concertTitle(concertTitle)
                .user(user)
                .build();
    }

    public void addBookingSeat(BookingSeat bookingSeat) {
        boolean hasNotDuplicated = bookingSeats.stream()
                    .noneMatch(thisBookingSeat -> thisBookingSeat.equals(bookingSeat));

        if (hasNotDuplicated) {
            this.bookingSeats.add(bookingSeat);
        }
    }

    public void addAllBookingSeats(List<BookingSeat> bookingSeats) {
        for (BookingSeat bookingSeat : bookingSeats) {
            addBookingSeat(bookingSeat);
        }
    }

    public void markAsComplete() {
        this.bookingStatus = COMPLETE;
    }

    public void validateAlreadyBooked() {
        if (bookingStatus == COMPLETE) {
            throw new RestApiException(ALREADY_BOOKED);
        }
    }

    public void validatePendingBooking(LocalDateTime dateTime) {
        if (isBookingDateTimeValid(dateTime)) {
            log.info("BookingErrorCode.PENDING_BOOKING 발생");
            throw new RestApiException(PENDING_BOOKING);
        }
    }

    public int getTotalPrice() {
        return this.bookingSeats.stream()
                .mapToInt(bs -> bs.getSeat().getPrice())
                .sum();
    }

    public void reserveAllSeats() {
        bookingSeats.stream()
                .map(BookingSeat::getSeat)
                .forEach(seat -> seat.markAsBooked());
    }

    public long getPassedMinutesSinceBookingFrom(LocalDateTime verificationTime) {
        return calculateDurationSinceBookingFrom(verificationTime).toMinutes();
    }

    private boolean isBookingDateTimeValid(LocalDateTime dateTime) {
        return getPassedMinutesSinceBookingFrom(dateTime) < ALLOWED_MINUTES.getMinutes();
    }

    private Duration calculateDurationSinceBookingFrom(LocalDateTime dateTime) {
        return Duration.between(this.getBookingDateTime(), dateTime);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Booking booking = (Booking) object;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
