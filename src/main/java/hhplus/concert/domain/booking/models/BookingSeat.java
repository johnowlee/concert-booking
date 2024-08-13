package hhplus.concert.domain.booking.models;

import hhplus.concert.domain.concert.models.Seat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookingSeat that = (BookingSeat) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
