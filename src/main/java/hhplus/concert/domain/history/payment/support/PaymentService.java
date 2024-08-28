package hhplus.concert.domain.history.payment.support;

import hhplus.concert.domain.history.balance.components.BalanceWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.event.PaymentCompleteEvent;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.support.event.EventPublisher;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentService {

    private final EventPublisher eventPublisher;
    private final BalanceWriter balanceWriter;
    private final ClockManager clockManager;
    private final PaymentValidator paymentValidator;

    public void pay(Payment payment) {

        // 결제 가능 시간 초과 검증
        paymentValidator.validatePayableTime(payment);

        // 결제자 ID 검증
        paymentValidator.validatePayer(payment);

        // 잔액 검증
        paymentValidator.validatePayability(payment);

        User payer = payment.getUser();
        long totalPrice = payment.getBooking().getTotalPrice();

        // 잔액 사용
        payer.useBalance(totalPrice);

        // 결제 완료 이벤트 발행
        eventPublisher.publish(PaymentCompleteEvent.from(payment));

        // 잔액내역 save
        balanceWriter.saveUseBalance(payer, totalPrice, clockManager);
    }
}
