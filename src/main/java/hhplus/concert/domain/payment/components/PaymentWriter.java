package hhplus.concert.domain.payment.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.payment.models.Payment;
import hhplus.concert.domain.payment.repositories.PaymentWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentWriter {

    private final PaymentWriterRepository paymentWriterRepository;

    public Payment payBooking(Booking booking) {
        return paymentWriterRepository.save(Payment.createBookingPayment(booking));
    }
}
