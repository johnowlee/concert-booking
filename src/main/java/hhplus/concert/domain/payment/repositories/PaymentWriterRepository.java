package hhplus.concert.domain.payment.repositories;

import hhplus.concert.domain.payment.models.Payment;

public interface PaymentWriterRepository {
    Payment save(Payment payment);
}
