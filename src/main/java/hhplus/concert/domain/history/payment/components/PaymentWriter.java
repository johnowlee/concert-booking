package hhplus.concert.domain.history.payment.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.repositories.PaymentWriterRepository;
import hhplus.concert.domain.history.payment.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentWriter {

    private final PaymentWriterRepository paymentWriterRepository;

    public Payment save(Booking booking, LocalDateTime paymentDateTime) {
        return paymentWriterRepository.save(Payment.createPayment(booking, paymentDateTime));
    }
}
