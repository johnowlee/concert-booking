package hhplus.concert.api.booking.dto.response.booking;

import hhplus.concert.domain.concert.models.Seat;

public record BookingSeatDto(Long seatId, String seatNo) {
    public static BookingSeatDto from(Seat seat) {
        return new BookingSeatDto(seat.getId(), seat.getSeatNo());
    }
}
