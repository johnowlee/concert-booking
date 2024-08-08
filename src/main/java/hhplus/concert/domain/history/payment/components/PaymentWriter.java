package hhplus.concert.domain.history.payment.components;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.repositories.PaymentWriterRepository;
import hhplus.concert.domain.history.payment.models.Payment;
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
