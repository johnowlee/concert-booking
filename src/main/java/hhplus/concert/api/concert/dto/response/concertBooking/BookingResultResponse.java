package hhplus.concert.api.concert.dto.response.concertBooking;

import hhplus.concert.api.common.ResponseResult;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.concert.models.ConcertOption;
import hhplus.concert.domain.concert.models.Seat;
import hhplus.concert.domain.user.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BookingResultResponse(ResponseResult bookingResult,
                                    LocalDateTime bookingDateTime,
                                    String bookingUserName,
                                    String concertTitle,
                                    LocalDateTime concertDateTime,
                                    List<String> seats) {

    public static BookingResultResponse succeed(
                                           User user,
                                           Booking booking,
                                           ConcertOption concertoption,
                                           List<Seat> seats) {
        return new BookingResultResponse(
                ResponseResult.SUCCESS,
                booking.getBookingDateTime(),
                user.getName(),
                booking.getConcertTitle(),
                concertoption.getConcertDateTime(),
                getSeatNos(seats)
        );
    }

    private static List<String> getSeatNos(List<Seat> seats) {
        return seats.stream()
                .map(s -> s.getSeatNo())
                .collect(Collectors.toList());
    }

    public static BookingResultResponse fail() {
        return new BookingResultResponse(ResponseResult.FAILURE, null, null, null, null, null);
    }
}
