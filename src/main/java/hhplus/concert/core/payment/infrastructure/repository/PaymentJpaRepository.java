package hhplus.concert.core.payment.infrastructure.repository;

import hhplus.concert.core.payment.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
