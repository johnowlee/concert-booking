package hhplus.concert.domain.concert.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    private String seatNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private SeatBookingStatus seatBookingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_option_id")
    private ConcertOption concertOption;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private SeatGrade grade;

    @Builder
    private Seat(String seatNo, SeatBookingStatus seatBookingStatus, ConcertOption concertOption, SeatGrade grade) {
        this.seatNo = seatNo;
        this.seatBookingStatus = seatBookingStatus;
        this.concertOption = concertOption;
        this.grade = grade;
    }

    public void markAsProcessing() {
        this.seatBookingStatus = SeatBookingStatus.PROCESSING;
    }

    public void markAsBooked() {
        this.seatBookingStatus = SeatBookingStatus.BOOKED;
    }

    public boolean isBooked() {
        return this.seatBookingStatus == SeatBookingStatus.BOOKED;
    }

    public int getPrice() {
        return this.grade.price;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Seat seat = (Seat) object;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
