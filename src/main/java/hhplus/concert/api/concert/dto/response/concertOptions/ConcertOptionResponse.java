package hhplus.concert.api.concert.dto.response.concertOptions;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ConcertOptionResponse(Long concertOptioinId,
                                    String place,
                                    LocalDateTime dateTime,
                                    List<SeatDto> seats) {
    public static ConcertOptionResponse from(ConcertOption concertOption) {
        return new ConcertOptionResponse(
                concertOption.getId(),
                concertOption.getPlace(),
                concertOption.getConcertDateTime(),
                toSeatDtos(concertOption.getSeats())
        );
    }

    private static List<SeatDto> toSeatDtos(List<Seat> seats) {
        return seats.stream()
                .map(s -> SeatDto.from(s))
                .collect(Collectors.toList());
    }
}
