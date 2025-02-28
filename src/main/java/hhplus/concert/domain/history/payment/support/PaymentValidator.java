package hhplus.concert.domain.history.payment.support;

import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.BalanceErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static hhplus.concert.representer.exception.code.PaymentErrorCode.INVALID_PAYER;
import static hhplus.concert.representer.exception.code.PaymentErrorCode.PAYABLE_TIME_OVER;
import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;

@Component
public class PaymentValidator {

    public void validatePayerEquality(Payment payment) {
        User booker = payment.getBooking().getUser();
        User payer = payment.getUser();
        if (booker.doesNotEqual(payer)) {
            throw new RestApiException(INVALID_PAYER);
        }
    }

    public void validatePayableTime(Payment payment) {
        Booking booking = payment.getBooking();
        LocalDateTime paymentDateTIme = payment.getPaymentDateTime();
        long passedMinutes = booking.getPassedMinutesSinceBookingFrom(paymentDateTIme);
        if (isPayableTimeOver(passedMinutes)) {
            throw new RestApiException(PAYABLE_TIME_OVER);
        }
    }

    public void checkPayerBalance(Payment payment) {
        User payer = payment.getUser();
        int totalPrice = payment.getPaymentAmount();
        if (payer.isBalanceLessThan(totalPrice)) {
            throw new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE);
        }
    }

    private boolean isPayableTimeOver(long passedMinutes) {
        return passedMinutes >= ALLOWED_MINUTES.getMinutes();
    }

}
