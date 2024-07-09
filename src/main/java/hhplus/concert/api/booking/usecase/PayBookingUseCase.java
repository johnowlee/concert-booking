package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.response.payment.PaymentResponse;
import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.booking.service.BookingManager;
import hhplus.concert.domain.payment.components.PaymentWriter;
import hhplus.concert.domain.payment.event.PaymentCompleteEvent;
import hhplus.concert.domain.support.event.EventPublisher;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.common.ResponseResult.SUCCESS;
import static hhplus.concert.domain.balance.models.TransactionType.USE;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayBookingUseCase {

    private final BookingReader bookingReader;
    private final PaymentWriter paymentWriter;
    private final BalanceHistoryWriter balanceHistoryWriter;
    private final UserReader userReader;
    private final EventPublisher eventPublisher;
    private final BookingManager bookingManager;

    @Transactional
    public PaymentResponse execute(Long id, Long userId) {

        Booking booking = bookingReader.getBookingById(id);

        // 예약시간초과 검증
        booking.validateBookingDateTime();

        User user = userReader.getUserById(userId);

        // 잔액검증 및 user 잔액 update
        long amount = booking.getTotalPrice();
        user.useBalance(amount);

        // 결제 완료 이벤트 발행
        eventPublisher.publish(PaymentCompleteEvent.of(user, booking));

        // 잔액내역 save
        balanceHistoryWriter.saveBalanceHistory(user, amount, USE);
        // 결제 내역 save
        paymentWriter.payBooking(booking, amount);


        // 예약 완료
        booking.markAsComplete();

        // 좌석 예약
        bookingManager.reserveAllSeats(booking);

        return PaymentResponse.from(SUCCESS);
    }
}
