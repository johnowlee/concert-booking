package hhplus.concert.core.history.payment.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

//    @InjectMocks
//    PaymentService paymentService;
//
//    @Mock
//    PaymentValidator paymentValidator;
//
//    @DisplayName("결제 시 결제 검증을 통과 하고 잔액을 차감 한다.")
//    @Test
//    void pay() {
//        // given
//        User payer = mock(User.class);
//        Payment payment = mock(Payment.class);
//        given(payment.getUser()).willReturn(payer);
//        given(payment.getPaymentAmount()).willReturn(100000);
//
//        doNothing().when(paymentValidator).validatePayableTime(payment);
//        doNothing().when(paymentValidator).validatePayerEquality(payment);
//        doNothing().when(paymentValidator).checkPayerBalance(payment);
//
//        // when
//        paymentService.pay(payment);
//
//        // then
//        then(paymentValidator).should(times(1)).validatePayableTime(payment);
//        then(paymentValidator).should(times(1)).validatePayerEquality(payment);
//        then(paymentValidator).should(times(1)).checkPayerBalance(payment);
//        then(payer).should(times(1)).useBalance(100000);
//    }
//
//    @DisplayName("결제 시 결제 가능 시간 검증에 실패하면 예외를 던진다.")
//    @Test
//    void payFailsPayableTimeValidation() {
//        // given
//        Payment payment = mock(Payment.class);
//
//        doThrow(new RestApiException(PAYABLE_TIME_OVER)).when(paymentValidator).validatePayableTime(payment);
//
//        // when & then
//        Assertions.assertThatThrownBy(() -> paymentService.pay(payment))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(PAYABLE_TIME_OVER.getMessage());
//    }
//
//    @DisplayName("결제 시 결제자 검증에 실패하면 예외를 던진다.")
//    @Test
//    void payFailsPayerEqualityValidation() {
//        // given
//        Payment payment = mock(Payment.class);
//
//        doThrow(new RestApiException(INVALID_PAYER)).when(paymentValidator).validatePayerEquality(payment);
//
//        // when & then
//        Assertions.assertThatThrownBy(() -> paymentService.pay(payment))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(INVALID_PAYER.getMessage());
//    }
//
//    @DisplayName("결제 시 결제자 잔액 검증에 실패하면 예외를 던진다.")
//    @Test
//    void payFailsPayerBalanceChecking() {
//        // given
//        Payment payment = mock(Payment.class);
//
//        doThrow(new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE)).when(paymentValidator).checkPayerBalance(payment);
//
//        // when & then
//        Assertions.assertThatThrownBy(() -> paymentService.pay(payment))
//                .isInstanceOf(RestApiException.class)
//                .hasMessage(BalanceErrorCode.NOT_ENOUGH_BALANCE.getMessage());
//    }
}