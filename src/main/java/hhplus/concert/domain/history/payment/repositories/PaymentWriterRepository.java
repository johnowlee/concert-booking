package hhplus.concert.domain.history.payment.repositories;

import hhplus.concert.domain.history.payment.models.Payment;

public interface PaymentWriterRepository {
    Payment save(Payment payment);
}
