package hhplus.concert.domain.payment.infrastructure;

import hhplus.concert.domain.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
