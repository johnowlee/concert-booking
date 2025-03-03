package hhplus.concert.core.payment.domain.service;

import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.core.payment.domain.port.PaymentCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCommandService {

    private final PaymentCommandPort paymentCommandPort;

    public Payment save(Payment payment) {
        return paymentCommandPort.save(payment);
    }
}
