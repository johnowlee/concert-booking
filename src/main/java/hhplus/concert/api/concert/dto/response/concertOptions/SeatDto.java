package hhplus.concert.api.concert.dto.response.concertOptions;

import hhplus.concert.domain.concert.models.BookingStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public record SeatDto(Long seatId, String seatNo, BookingStatus bookingStatus) {
    public static SeatDto of(Long seatId, String seatNo, BookingStatus bookingStatus) {
        return new SeatDto(seatId, seatNo, bookingStatus);
    }
}
