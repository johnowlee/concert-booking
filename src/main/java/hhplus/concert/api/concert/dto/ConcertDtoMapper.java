package hhplus.concert.api.concert.dto;

import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ConcertDtoMapper {
    public static List<ConcertDto> createConcertDtos(List<Concert> concerts) {
        return concerts.stream()
                .map(c -> createConcertDto(c))
                .collect(Collectors.toList());
    }

    public static ConcertDto createConcertDto(Concert concert) {
        return ConcertDto.builder()
                .concertId(concert.getConcertId())
                .title(concert.getTitle())
                .organizer(concert.getOrganizer())
                .concertOptions(createConcertOptionDtos(concert.getConcertOptions()))
                .build();
    }

    public static List<ConcertOptionDto> createConcertOptionDtos(List<ConcertOption> concertOptions) {
        return concertOptions.stream()
                .map(co -> createConcertOptionDto(co))
                .collect(Collectors.toList());
    }

    public static ConcertOptionDto createConcertOptionDto(ConcertOption concertOption) {
        return ConcertOptionDto.builder()
                .concertOptionId(concertOption.getId())
                .place(concertOption.getPlace())
                .dateTime(concertOption.getConcertDateTime())
                .availableSeatsCount(0)
                .build();
    }

    public static List<SeatDto> createSeatDtos(List<Seat> seats) {
        return seats.stream()
                .map(s -> createSeatDto(s))
                .collect(Collectors.toList());
    }

    public static SeatDto createSeatDto(Seat seat) {
        return SeatDto.builder()
                .seatId(seat.getId())
                .seatNo(seat.getSeatNo())
                .bookingStatus(seat.getBookingStatus())
                .build();
    }
}
