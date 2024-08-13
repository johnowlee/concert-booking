package hhplus.concert.domain.booking.models;

import hhplus.concert.domain.concert.models.Seat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "booking_seat")
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Builder
    private BookingSeat(Long id, Booking booking, Seat seat) {
        this.id = id;
        this.seat = seat;
        this.booking = booking;
    }

    private void setBooking(Booking booking) {
        this.booking = booking;
        booking.getBookingSeats().add(this);
    }

    public static BookingSeat createBookingSeat(Booking booking, Seat seat) {
        return builder()
                .booking(booking)
                .seat(seat)
                .build();
    }
}
