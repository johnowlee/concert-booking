package hhplus.concert.domain.history.payment.components;

import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.repositories.PaymentWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentWriter {

    private final PaymentWriterRepository paymentWriterRepository;

    public Payment save(Payment payment) {
        return paymentWriterRepository.save(payment);
    }
}
