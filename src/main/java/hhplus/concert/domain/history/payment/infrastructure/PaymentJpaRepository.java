package hhplus.concert.domain.history.payment.infrastructure;

import hhplus.concert.domain.history.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
