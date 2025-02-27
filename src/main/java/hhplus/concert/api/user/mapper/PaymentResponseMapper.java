package hhplus.concert.api.user.mapper;

import hhplus.concert.api.user.response.PaymentResponse;
import hhplus.concert.domain.history.payment.models.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseMapper {

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getPaymentDateTime(), payment.getPaymentAmount());
    }

}
