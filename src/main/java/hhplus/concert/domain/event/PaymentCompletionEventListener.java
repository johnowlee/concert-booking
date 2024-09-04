package hhplus.concert.domain.event;

import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.payment.components.PaymentHistoryWriter;
import hhplus.concert.domain.history.payment.event.PaymentCompletion;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class PaymentCompletionEventListener {

    private final BookingReader bookingReader;
    private final UserReader userReader;
    private final ClockManager clockManager;
    private final PaymentHistoryWriter paymentHistoryWriter;
    private final BalanceHistoryWriter balanceHistoryWriter;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransactionRecords(PaymentCompletion paymentCompletion) {
        Payment payment = createPayment(paymentCompletion.bookingId(), paymentCompletion.payerId());
        balanceHistoryWriter.save(Balance.createUseBalanceFrom(payment));
        paymentHistoryWriter.save(payment);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeBooking(PaymentCompletion paymentCompletion) {
        Booking booking = bookingReader.getBookingById(paymentCompletion.bookingId());
        booking.markAsComplete();
        booking.markSeatsAsBooked();
    }

    private Payment createPayment(Long bookingId, Long userId) {
        Booking booking = bookingReader.getBookingById(bookingId);
        User payer = userReader.getUserById(userId);
        LocalDateTime paymentDateTime = clockManager.getNowDateTime();
        return Payment.of(booking, payer, paymentDateTime);
    }
}
