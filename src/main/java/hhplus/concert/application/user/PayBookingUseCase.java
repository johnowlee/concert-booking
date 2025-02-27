package hhplus.concert.application.user;

import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.event.PaymentCompletion;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.service.PaymentService;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentService paymentService;
    private final ClockManager clockManager;
    private final UserReader userReader;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Payment execute(Long userId, Long bookingId) {
        Payment payment = createPayment(userId, bookingId);
        paymentService.pay(payment);
        eventPublisher.publishEvent(PaymentCompletion.from(payment));
        return payment;
    }

    private Payment createPayment(Long userId, Long bookingId) {
        User payer = userReader.getUserById(userId);
        Booking booking = bookingReader.getBookingById(bookingId);
        LocalDateTime paymentDateTime = clockManager.getNowDateTime();
        return Payment.of(booking, payer, paymentDateTime);
    }
}
