package hhplus.concert.api.concert.dto.response.concertOptions;

import hhplus.concert.domain.concert.models.SeatBookingStatus;

public record SeatDto(Long seatId, String seatNo, SeatBookingStatus seatBookingStatus) {
    public static SeatDto of(Long seatId, String seatNo, SeatBookingStatus seatBookingStatus) {
        return new SeatDto(seatId, seatNo, seatBookingStatus);
    }
}
