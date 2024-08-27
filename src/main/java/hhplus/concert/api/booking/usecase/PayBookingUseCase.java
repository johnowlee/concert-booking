package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.api.common.response.PaymentResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.support.PaymentService;
import hhplus.concert.domain.history.payment.components.PaymentWriter;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentValidator;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentWriter paymentWriter;
    private final PaymentService paymentService;
    private final ClockManager clockManager;
    private final UserReader userReader;
    private final PaymentValidator paymentValidator;

    @Transactional
    public PaymentResponse execute(Long id, PaymentRequest request) {

        Booking booking = bookingReader.getBookingById(id);

        // 결제 가능 시간 초과 검증
        paymentValidator.validatePayableTime(booking, clockManager.getNowDateTime());

        // 결제자 ID 검증
        User payer = userReader.getUserById(request.userId());
        paymentValidator.validatePayer(booking, payer);

        // 결제
        paymentService.pay(booking);

        // 결제 내역 save
        Payment payment = paymentWriter.save(booking, clockManager.getNowDateTime());

        // 예약 완료
        booking.markAsComplete();

        // 좌석 예약
        booking.reserveAllSeats();

        return PaymentResponse.from(payment);
    }
}
