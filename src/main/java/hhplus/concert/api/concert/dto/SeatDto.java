package hhplus.concert.api.concert.dto;

import hhplus.concert.domain.concert.models.BookingStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SeatDto {

    private Long seatId;
    private String seatNo;
    private BookingStatus bookingStatus;

    @Builder
    private SeatDto(Long seatId, String seatNo, BookingStatus bookingStatus) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.bookingStatus = bookingStatus;
    }
}
