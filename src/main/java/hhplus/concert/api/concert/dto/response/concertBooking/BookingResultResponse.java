package hhplus.concert.api.concert.dto.response.concertBooking;

import hhplus.concert.api.common.ResponseResult;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.Concert;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BookingResultResponse(ResponseResult bookingResult, LocalDateTime bookingDateTime, String bookingUserName, BookingResultConcertDto concert) {

    public static BookingResultResponse of(ResponseResult bookingResult,
                                           User user,
                                           Booking booking,
                                           ConcertOption concertoption,
                                           List<Seat> seats) {
        return new BookingResultResponse(
                bookingResult,
                booking.getBookingDateTime(),
                user.getName(),
                createBookingResultConcertDto(booking.getConcertTitle(), concertoption.getConcertDateTime(), seats)
        );
    }

    private static BookingResultConcertDto createBookingResultConcertDto(String concertTitle,
                                                                         LocalDateTime concertDateTime,
                                                                         List<Seat> seats) {

        return BookingResultConcertDto.of(concertTitle, concertDateTime, getSeatsNo(seats));
    }

    private static List<String> getSeatsNo(List<Seat> seats) {
        return seats.stream()
                .map(s -> s.getSeatNo())
                .collect(Collectors.toList());
    }
}
