package hhplus.concert.application.user.usecase;

import hhplus.concert.application.support.ClockManager;
import hhplus.concert.application.user.data.command.PaymentCompletionCommand;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.core.payment.domain.model.PaymentTimeLimitPolicy;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.service.UserQueryService;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.BalanceErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static hhplus.concert.representer.exception.code.PaymentErrorCode.INVALID_PAYER;
import static hhplus.concert.representer.exception.code.PaymentErrorCode.PAYABLE_TIME_OVER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PayBookingUseCase {

    private final UserQueryService userQueryService;
    private final BookingQueryService bookingQueryService;
    private final ClockManager clockManager;

    private final ApplicationEventPublisher eventPublisher;

    public Payment execute(Long userId, Long bookingId) {
        Payment payment = createPayment(userId, bookingId);
        validatePayment(payment);
        User payer = payment.getUser();
        payer.useBalance(payment.getPaymentAmount());
        eventPublisher.publishEvent(PaymentCompletionCommand.from(payment));
        return payment;
    }

    private Payment createPayment(Long userId, Long bookingId) {
        User payer = userQueryService.getUserById(userId);
        Booking booking = bookingQueryService.getBookingById(bookingId);
        LocalDateTime paymentDateTime = clockManager.getNowDateTime();
        return Payment.of(booking, payer, paymentDateTime);
    }

    private void validatePayment(Payment payment) {
        validatePayableTime(payment);
        validatePayerEquality(payment);
        checkPayerBalance(payment);
    }

    private void validatePayableTime(Payment payment) {
        Booking booking = payment.getBooking();
        LocalDateTime paymentDateTIme = payment.getPaymentDateTime();
        long passedMinutes = booking.getPassedMinutesSinceBookingFrom(paymentDateTIme);
        if (isPayableTimeOver(passedMinutes)) {
            throw new RestApiException(PAYABLE_TIME_OVER);
        }
    }

    private boolean isPayableTimeOver(long passedMinutes) {
        return passedMinutes >= PaymentTimeLimitPolicy.ALLOWED_MINUTES.getMinutes();
    }

    private void validatePayerEquality(Payment payment) {
        User booker = payment.getBooking().getUser();
        User payer = payment.getUser();
        if (booker.notEquals(payer)) {
            throw new RestApiException(INVALID_PAYER);
        }
    }

    private void checkPayerBalance(Payment payment) {
        User payer = payment.getUser();
        int totalPrice = payment.getPaymentAmount();
        if (payer.isBalanceLessThan(totalPrice)) {
            throw new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE);
        }
    }

}

