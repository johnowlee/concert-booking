package hhplus.concert.domain.history.payment.service;

import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentValidator;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentValidator paymentValidator;

    public void pay(Payment payment) {

        validatePay(payment);

        useBalance(payment);
    }

    private void validatePay(Payment payment) {
        paymentValidator.validatePayableTime(payment);

        paymentValidator.validatePayerEquality(payment);

        paymentValidator.checkPayerBalance(payment);
    }

    private static void useBalance(Payment payment) {
        User payer = payment.getUser();
        long totalPrice = payment.getPaymentAmount();
        payer.useBalance(totalPrice);
    }

}
