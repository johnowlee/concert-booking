package hhplus.concert.domain.history.payment.support;

import hhplus.concert.domain.history.balance.components.BalanceWriter;
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

        validatePay(payment);

        User payer = payment.getUser();
        long totalPrice = payment.getBooking().getTotalPrice();

        payer.useBalance(totalPrice);

        eventPublisher.publish(PaymentCompleteEvent.from(payment));

        balanceWriter.saveUseBalance(payer, totalPrice, clockManager);
    }

    private void validatePay(Payment payment) {
        paymentValidator.validatePayableTime(payment);

        paymentValidator.validatePayerEquality(payment);

        paymentValidator.checkPayerBalance(payment);
    }
}
