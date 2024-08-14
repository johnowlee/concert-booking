package hhplus.concert.api.concert.usecase.response.concertOptions;

import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;

public record SeatDto(Long seatId, String seatNo, SeatBookingStatus seatBookingStatus) {
    public static SeatDto from(Seat seat) {
        return new SeatDto(seat.getId(), seat.getSeatNo(), seat.getSeatBookingStatus());
    }
}
