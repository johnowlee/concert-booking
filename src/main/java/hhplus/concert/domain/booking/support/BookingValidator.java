package hhplus.concert.domain.booking.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.api.exception.code.BookingErrorCode.PENDING_BOOKING;
import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;

@Slf4j
@Component
public class BookingValidator {

    public void checkAnyAlreadyCompleteBooking(List<Booking> bookings) {
        bookings.forEach(Booking::validateAlreadyBooked);
    }

    public void checkAnyPendingBooking(List<Booking> bookings, LocalDateTime localDateTime) {
        bookings.forEach(booking -> validatePendingBooking(booking, localDateTime));
    }

    private static void validatePendingBooking(Booking booking, LocalDateTime verificationTime) {
        long passedMinutes = booking.getPassedMinutesSinceBookingFrom(verificationTime);
        if (isPayableTimeNotOver(passedMinutes)) {
            throw new RestApiException(PENDING_BOOKING);
        }
    }

    private static boolean isPayableTimeNotOver(long passedMinutes) {
        return passedMinutes < ALLOWED_MINUTES.getMinutes();
    }
}
