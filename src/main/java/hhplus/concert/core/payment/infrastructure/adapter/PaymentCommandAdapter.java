package hhplus.concert.core.payment.infrastructure.adapter;

import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.core.payment.infrastructure.repository.PaymentJpaRepository;
import hhplus.concert.core.payment.domain.port.PaymentCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentCommandAdapter implements PaymentCommandPort {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
}
