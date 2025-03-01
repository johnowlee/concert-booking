package hhplus.concert.core.booking.domain.model;

import hhplus.concert.core.concert.domain.model.Seat;
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
    private BookingSeat(Booking booking, Seat seat) {
        this.seat = seat;
        this.booking = booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
        booking.addBookingSeat(this);
    }

    public static BookingSeat createBookingSeat(Booking booking, Seat seat) {
        BookingSeat bookingSeat = builder()
                .seat(seat)
                .build();
        bookingSeat.setBooking(booking);
        return bookingSeat;
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
