package hhplus.concert.domain.booking.support;

import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

import static hhplus.concert.representer.exception.code.BookingErrorCode.ALREADY_BOOKED;
import static hhplus.concert.representer.exception.code.BookingErrorCode.PENDING_BOOKING;
import static hhplus.concert.domain.booking.models.BookingStatus.COMPLETE;
import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;

@Slf4j
@Component
public class BookingValidator {

    public void checkAnyAlreadyCompleteBooking(Set<Booking> bookings) {
        bookings.forEach(BookingValidator::validateAlreadyBooked);
    }

    public void checkAnyPendingBooking(Set<Booking> bookings, LocalDateTime localDateTime) {
        bookings.forEach(booking -> validatePendingBooking(booking, localDateTime));
    }

    private static void validateAlreadyBooked(Booking booking) {
        if (booking.getBookingStatus() == COMPLETE) {
            throw new RestApiException(ALREADY_BOOKED);
        }
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
