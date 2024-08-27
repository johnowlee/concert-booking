package hhplus.concert.domain.history.payment.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.user.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static hhplus.concert.api.exception.code.BookingErrorCode.EXPIRED_BOOKING_TIME;
import static hhplus.concert.api.exception.code.BookingErrorCode.INVALID_PAYER;
import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;

@Component
public class PaymentValidator {

    public void validatePayer(Booking booking, User payer) {
        User booker = booking.getUser();
        if (booker.doesNotEqual(payer)) {
            throw new RestApiException(INVALID_PAYER);
        }
    }

    public void validatePayableTime(Booking booking, LocalDateTime verificationTime) {
        long passedMinutes = booking.getPassedMinutesSinceBookingFrom(verificationTime);
        if (isOverPayableTime(passedMinutes)) {
            throw new RestApiException(EXPIRED_BOOKING_TIME);
        }
    }

    private static boolean isOverPayableTime(long passedMinutes) {
        return passedMinutes >= ALLOWED_MINUTES.getMinutes();
    }
}
