package hhplus.concert.domain.concert.support;

import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.domain.concert.models.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static hhplus.concert.representer.exception.code.BookingErrorCode.ALREADY_BOOKED;

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
