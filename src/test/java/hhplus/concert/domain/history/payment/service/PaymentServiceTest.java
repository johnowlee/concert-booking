package hhplus.concert.domain.history.payment.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.event.PaymentCompletionEvent;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentValidator;
import hhplus.concert.domain.user.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static hhplus.concert.api.exception.code.PaymentErrorCode.INVALID_PAYER;
import static hhplus.concert.api.exception.code.PaymentErrorCode.PAYABLE_TIME_OVER;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentValidator paymentValidator;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @DisplayName("결제 시 결제 검증을 통과 하고 잔액 차감 후 결제완료 이벤트를 발행한다.")
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

    @DisplayName("결제 시 결제 가능 시간 검증에 실패하면 예외를 던지고 결제완료 이벤트를 발행하지 않는다.")
    @Test
    void payFailsPayableTimeValidation() {
        // given
        User payer = mock(User.class);
        Booking booking = mock(Booking.class);

        given(payer.getId()).willReturn(1L);
        given(booking.getUser()).willReturn(payer);
        given(booking.getTotalPrice()).willReturn(10000);

        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        Payment payment = Payment.of(booking, payer, paymentDateTime);

        doThrow(new RestApiException(PAYABLE_TIME_OVER)).when(paymentValidator).validatePayableTime(payment);

        // when & then
        Assertions.assertThatThrownBy(() -> paymentService.pay(payment))
                        .isInstanceOf(RestApiException.class)
                        .hasMessage(PAYABLE_TIME_OVER.getMessage());
        then(eventPublisher).should(never()).publishEvent(PaymentCompletionEvent.from(payment));
    }

    @DisplayName("결제 시 결제자 검증에 실패하면 예외를 던지고 결제완료 이벤트를 발행하지 않는다.")
    @Test
    void payFailsPayerEqualityValidation() {
        // given
        User payer = mock(User.class);
        Booking booking = mock(Booking.class);

        given(payer.getId()).willReturn(1L);
        given(booking.getUser()).willReturn(payer);
        given(booking.getTotalPrice()).willReturn(10000);

        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        Payment payment = Payment.of(booking, payer, paymentDateTime);

        doThrow(new RestApiException(INVALID_PAYER)).when(paymentValidator).validatePayerEquality(payment);

        // when & then
        Assertions.assertThatThrownBy(() -> paymentService.pay(payment))
                .isInstanceOf(RestApiException.class)
                .hasMessage(INVALID_PAYER.getMessage());
        then(eventPublisher).should(never()).publishEvent(PaymentCompletionEvent.from(payment));
    }

    @DisplayName("결제 시 결제자 잔액 검증에 실패하면 예외를 던지고 결제완료 이벤트를 발행하지 않는다.")
    @Test
    void payFailsPayerBalanceChecking() {
        // given
        User payer = mock(User.class);
        Booking booking = mock(Booking.class);

        given(payer.getId()).willReturn(1L);
        given(booking.getUser()).willReturn(payer);
        given(booking.getTotalPrice()).willReturn(10000);

        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        Payment payment = Payment.of(booking, payer, paymentDateTime);

        doThrow(new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE)).when(paymentValidator).checkPayerBalance(payment);

        // when & then
        Assertions.assertThatThrownBy(() -> paymentService.pay(payment))
                .isInstanceOf(RestApiException.class)
                .hasMessage(BalanceErrorCode.NOT_ENOUGH_BALANCE.getMessage());
        then(eventPublisher).should(never()).publishEvent(PaymentCompletionEvent.from(payment));
    }
}