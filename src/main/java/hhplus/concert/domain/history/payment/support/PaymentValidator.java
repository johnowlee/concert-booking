package hhplus.concert.domain.history.payment.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.user.models.User;
import org.springframework.stereotype.Component;

import static hhplus.concert.api.exception.code.BookingErrorCode.INVALID_PAYER;

@Component
public class PaymentValidator {

    public void validatePayer(Booking booking, User payer) {
        User booker = booking.getUser();
        if (!booker.equals(payer)) {
            throw new RestApiException(INVALID_PAYER);
        }
    }
}
