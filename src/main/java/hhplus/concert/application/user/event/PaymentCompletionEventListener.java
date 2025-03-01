package hhplus.concert.application.user.event;

import hhplus.concert.application.user.dto.PaymentCompletion;
import hhplus.concert.core.booking.domain.service.BookingQueryService;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.transaction.domain.service.TransactionCommandService;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.core.payment.domain.service.PaymentCommandService;
import hhplus.concert.application.support.ClockManager;
import hhplus.concert.core.user.domain.service.UserQueryService;
import hhplus.concert.core.user.domain.model.User;
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

    private final BookingQueryService bookingQueryService;
    private final UserQueryService userQueryService;
    private final ClockManager clockManager;
    private final PaymentCommandService paymentCommandService;
    private final TransactionCommandService transactionCommandService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransactionRecords(PaymentCompletion paymentCompletion) {
        Payment payment = createPayment(paymentCompletion.bookingId(), paymentCompletion.payerId());
        transactionCommandService.save(Transaction.createUseBalanceFrom(payment));
        paymentCommandService.save(payment);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeBooking(PaymentCompletion paymentCompletion) {
        Booking booking = bookingQueryService.getBookingById(paymentCompletion.bookingId());
        booking.markAsComplete();
        booking.markSeatsAsBooked();
    }

    private Payment createPayment(Long bookingId, Long userId) {
        Booking booking = bookingQueryService.getBookingById(bookingId);
        User payer = userQueryService.getUserById(userId);
        LocalDateTime paymentDateTime = clockManager.getNowDateTime();
        return Payment.of(booking, payer, paymentDateTime);
    }
}
