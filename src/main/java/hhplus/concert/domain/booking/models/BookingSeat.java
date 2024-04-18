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
    @GeneratedValue
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
        this.booking = booking;
        this.seat = seat;
    }

    public BookingSeat toBookingSeat() {
        return BookingSeat.builder()
//                .bookingSeatId(id)
                .booking(booking.toBooking())
//                .seat(seatEntity.toSeat())
                .build();
    }

    @Builder
    public static BookingSeat toBookingSeatEntity(BookingSeat bookingSeat) {
        return builder()
//                .id(bookingSeat.getBookingSeatId())
//                .bookingEntity(Booking.toBookingEntity(bookingSeat.getBooking()))
//                .seatEntity(SeatEntity.toSeatEntity(bookingSeat.getSeat()))
                .build();
    }

}
