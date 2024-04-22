package hhplus.concert.domain.payment.infrastructure;

import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.payment.repositories.PaymentWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentCoreWriterRepository implements PaymentWriterRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }
}
