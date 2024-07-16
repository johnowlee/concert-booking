package hhplus.concert.domain.balance.service;

import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.payment.event.PaymentCompleteEvent;
import hhplus.concert.domain.support.event.EventPublisher;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceService {

    private final UserReader userReader;
    private final EventPublisher eventPublisher;
    private final BalanceHistoryWriter balanceHistoryWriter;

    public void use(Long userId, Booking booking) {
        User user = userReader.getUserById(userId);

        // 잔액검증 및 user 잔액 update
        long amount = booking.getTotalPrice();
        user.useBalance(amount);

        // 결제 완료 이벤트 발행
        eventPublisher.publish(PaymentCompleteEvent.from(booking));

        // 잔액내역 save
        balanceHistoryWriter.saveBalanceUseHistory(user, amount);
    }
}
