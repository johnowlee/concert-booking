package hhplus.concert.domain.concert.models;

import hhplus.concert.domain.booking.models.BookingSeat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat")
public class Seat {

    @Id @GeneratedValue
    @Column(name = "seat_id")
    private Long id;

    private String seatNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private SeatBookingStatus seatBookingStatus;

    @OneToMany(mappedBy = "seat")
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_option_id")
    private ConcertOption concertOption;

    @Builder
    private Seat(Long id, String seatNo, SeatBookingStatus seatBookingStatus, List<BookingSeat> bookingSeats, ConcertOption concertOption) {
        this.id = id;
        this.seatNo = seatNo;
        this.seatBookingStatus = seatBookingStatus;
        this.bookingSeats = bookingSeats;
        this.concertOption = concertOption;
    }
}
