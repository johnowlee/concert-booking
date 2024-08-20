package hhplus.concert.api.concert.usecase.response;

import hhplus.concert.api.common.response.ConcertOptionResponse;
import hhplus.concert.api.common.response.SeatResponse;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.util.List;
import java.util.stream.Collectors;

public record ConcertOptionWithSeatsResponse(ConcertOptionResponse concertOption, List<SeatResponse> seats) {
    public static ConcertOptionWithSeatsResponse of(ConcertOption concertOption, List<Seat> seats) {
        return new ConcertOptionWithSeatsResponse(
                ConcertOptionResponse.from(concertOption),
                seats.stream()
                        .map(s -> SeatResponse.from(s))
                        .collect(Collectors.toList())
        );
    }
}
