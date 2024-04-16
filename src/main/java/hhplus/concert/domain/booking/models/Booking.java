package hhplus.concert.domain.booking.models;

import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import hhplus.concert.entities.booking.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Booking {

    private Long id;
    private BookingStatus bookingStatus;
    private LocalDateTime bookingDateTime;
    private String concertTitle;
    private User user;
    private List<BookingSeat> bookingSeats;
    private Payment payment;

    @Builder
    private Booking(Long id,
                    BookingStatus bookingStatus,
                    LocalDateTime bookingDateTime,
                    String concertTitle,
                    User user,
                    @Singular List<BookingSeat> bookingSeats,
                    Payment payment) {
        this.id = id;
        this.bookingStatus = bookingStatus;
        this.bookingDateTime = bookingDateTime;
        this.concertTitle = concertTitle;
        this.user = user;
        this.bookingSeats = bookingSeats;
        this.payment = payment;
    }
}
