package hhplus.concert.representer.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatGrade;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@JsonInclude(NON_NULL)
public record SeatResponse(
        Long id,
        String seatNo,
        SeatBookingStatus seatBookingStatus,
        ConcertOptionResponse concertOption,
        SeatGrade grade
) {
    public static SeatResponse from(Seat seat) {
        return new SeatResponse(seat.getId(), seat.getSeatNo(), seat.getSeatBookingStatus(), null, seat.getGrade());
    }
}
