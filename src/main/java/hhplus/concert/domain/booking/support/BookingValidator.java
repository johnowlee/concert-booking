package hhplus.concert.domain.booking.support;

import hhplus.concert.domain.booking.models.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class BookingValidator {

    public void checkAnyAlreadyCompleteBooking(List<Booking> bookings) {
        bookings.forEach(booking -> booking.validateAlreadyBooked());
    }

    public void checkAnyPendingBooking(List<Booking> bookings, LocalDateTime localDateTime) {
        bookings.forEach(booking -> booking.validatePendingBooking(localDateTime));
    }
}
