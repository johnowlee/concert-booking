package hhplus.concert.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.SeatBookingStatus;
import hhplus.concert.domain.concert.models.SeatGrade;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record SeatResponse(
        Long id,
        String seatNo,
        SeatBookingStatus seatBookingStatus,
        ConcertOption concertOption,
        SeatGrade grade
) {}
