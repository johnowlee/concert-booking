package hhplus.concert.domain.concert.models;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Seat {

    private Long id;
    private String seatNo;
    private BookingStatus bookingStatus;

    @Builder
    private Seat(Long id, String seatNo, BookingStatus bookingStatus) {
        this.id = id;
        this.seatNo = seatNo;
        this.bookingStatus = bookingStatus;
    }
}
