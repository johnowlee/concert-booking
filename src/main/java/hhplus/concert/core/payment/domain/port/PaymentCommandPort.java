package hhplus.concert.core.payment.domain.port;

import hhplus.concert.core.payment.domain.model.Payment;

public interface PaymentCommandPort {
    Payment save(Payment payment);
}
