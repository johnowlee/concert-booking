package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.common.response.PaymentResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentService paymentService;
    private final ClockManager clockManager;
    private final UserReader userReader;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public PaymentResponse execute(Long id, PaymentRequest request) {
        Payment payment = createPayment(id, request);
        paymentService.pay(payment);
        eventPublisher.publishEvent(PaymentCompletion.from(payment));
        return PaymentResponse.from(payment);
    }

    private Payment createPayment(Long id, PaymentRequest request) {
        Booking booking = bookingReader.getBookingById(id);
        User payer = userReader.getUserById(request.userId());
        LocalDateTime paymentDateTime = clockManager.getNowDateTime();
        return Payment.of(booking, payer, paymentDateTime);
    }
}
