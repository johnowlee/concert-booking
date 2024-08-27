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

    public void pay(Booking booking) {
        // 잔액검증 및 user 잔액 update
        User user = booking.getUser();
        long amount = booking.getTotalPrice();
        user.useBalance(amount);

        // 결제 완료 이벤트 발행
        eventPublisher.publish(PaymentCompleteEvent.from(booking));

        // 잔액내역 save
        balanceWriter.saveUseBalance(user, amount, clockManager);
    }
}
