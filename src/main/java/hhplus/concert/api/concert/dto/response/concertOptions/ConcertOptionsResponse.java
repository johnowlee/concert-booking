package hhplus.concert.api.concert.dto.response.concertOptions;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.util.List;
import java.util.stream.Collectors;

public record ConcertOptionsResponse(List<ConcertOptionDto> concertOptions) {
    public static ConcertOptionsResponse from(List<ConcertOption> concertOptions) {
        return new ConcertOptionsResponse(
                concertOptions.stream()
                        .map(co -> ConcertOptionDto.from(co))
                        .collect(Collectors.toList())
        );
    }

    private static SeatDto toSeatDto(Seat s) {
        return SeatDto.of(s.getId(), s.getSeatNo(), s.getSeatBookingStatus());
    }
}
