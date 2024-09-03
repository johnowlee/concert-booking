package hhplus.concert.domain.history.payment.service;

import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.event.PaymentCompletionEvent;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentValidator;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentValidator paymentValidator;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @DisplayName("결제 시 결제 가능 여부를 검증하고 잔액 차감 후 결제완료 이벤트를 발행한다.")
    @Test
    void pay() {
        // given
        User payer = mock(User.class);
        Booking booking = mock(Booking.class);

        given(payer.getId()).willReturn(1L);
        given(booking.getUser()).willReturn(payer);
        given(booking.getTotalPrice()).willReturn(10000);

        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        Payment payment = Payment.of(booking, payer, paymentDateTime);

        // when
        paymentService.pay(payment);

        // then
        then(payer).should(times(1)).useBalance(10000L);
        then(eventPublisher).should(times(1)).publishEvent(PaymentCompletionEvent.from(payment));
        then(paymentValidator).should(times(1)).validatePayableTime(payment);
        then(paymentValidator).should(times(1)).validatePayerEquality(payment);
        then(paymentValidator).should(times(1)).checkPayerBalance(payment);
    }
}