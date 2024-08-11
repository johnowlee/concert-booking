package hhplus.concert.domain.booking.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.concert.models.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static hhplus.concert.api.exception.code.BookingErrorCode.ALREADY_BOOKED;

@Slf4j
@Component
public class SeatValidator {

    public void checkAnyBookedSeat(List<Seat> seats) {
        seats.stream()
                .filter(Seat::isBooked)
                .findAny()
                .ifPresent(seat -> {
                    log.error("BookingErrorCode.ALREADY_BOOKED occurred for seat: {}", seat);
                    throw new RestApiException(ALREADY_BOOKED);
                });
    }
}
