package hhplus.concert.api.concert.dto.response.concertOptions;

import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE, force = true)
public record ConcertOptionResponse(Long concertOptioinId,
                                    String title,
                                    String place,
                                    LocalDateTime dateTime,
                                    String organizer,
                                    List<SeatDto> seats) {
    public static ConcertOptionResponse from(ConcertOption concertOption) {
        return new ConcertOptionResponse(
                concertOption.getId(),
                concertOption.getConcert().getTitle(),
                concertOption.getPlace(),
                concertOption.getConcertDateTime(),
                concertOption.getConcert().getOrganizer(),
                createSeatDtos(concertOption.getSeats())
        );
    }

    private static List<SeatDto> createSeatDtos(List<Seat> seats) {
        return seats.stream()
                .map(s -> toSeatDto(s))
                .collect(Collectors.toList());
    }

    private static SeatDto toSeatDto(Seat s) {
        return SeatDto.of(s.getId(), s.getSeatNo(), s.getBookingStatus());
    }
}
