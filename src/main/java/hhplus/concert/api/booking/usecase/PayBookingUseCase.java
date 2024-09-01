package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.api.common.response.PaymentResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.payment.components.PaymentHistoryWriter;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentService;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentHistoryWriter paymentHistoryWriter;
    private final BalanceHistoryWriter balanceHistoryWriter;
    private final PaymentService paymentService;
    private final ClockManager clockManager;
    private final UserReader userReader;

    @Transactional
    public PaymentResponse execute(Long id, PaymentRequest request) {

        Payment payment = createPayment(id, request);

        paymentService.pay(payment);

        balanceHistoryWriter.saveBalance(Balance.createUseBalanceFrom(payment));

        paymentHistoryWriter.save(payment);

        completeBooking(payment);

        return PaymentResponse.from(payment);
    }

    private Payment createPayment(Long id, PaymentRequest request) {
        Booking booking = bookingReader.getBookingById(id);
        User payer = userReader.getUserById(request.userId());
        LocalDateTime paymentDateTime = clockManager.getNowDateTime();
        return Payment.of(booking, payer, paymentDateTime);
    }

    private void completeBooking(Payment payment) {
        payment.getBooking().markAsComplete();
        payment.getBooking().markSeatsAsBooked();
    }
}
