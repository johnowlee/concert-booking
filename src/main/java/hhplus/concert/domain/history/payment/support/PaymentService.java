package hhplus.concert.domain.history.payment.support;

import hhplus.concert.domain.history.balance.components.BalanceWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.event.PaymentCompleteEvent;
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

    public void pay(Booking booking) {

        User user = booking.getUser();
        long totalPrice = booking.getTotalPrice();

        // 잔액 검증
        paymentValidator.validatePayability(user, totalPrice);

        // 잔액 사용
        user.useBalance(totalPrice);

        // 결제 완료 이벤트 발행
        eventPublisher.publish(PaymentCompleteEvent.from(booking));

        // 잔액내역 save
        balanceWriter.saveUseBalance(user, totalPrice, clockManager);
    }
}
