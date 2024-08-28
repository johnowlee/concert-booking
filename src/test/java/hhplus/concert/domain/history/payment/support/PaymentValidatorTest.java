package hhplus.concert.domain.history.payment.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.user.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static hhplus.concert.api.exception.code.PaymentErrorCode.INVALID_PAYER;
import static hhplus.concert.api.exception.code.PaymentErrorCode.PAYABLE_TIME_OVER;
import static hhplus.concert.domain.history.payment.models.PaymentTimeLimitPolicy.ALLOWED_MINUTES;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PaymentValidatorTest {

    @DisplayName("예약자와 결제자가 동일하면 검증에 통과한다.")
    @Test
    void validatePayer() {
        // given
        Booking booking = mock(Booking.class);
        User booker = mock(User.class);

        given(booker.getId()).willReturn(1L);
        given(booking.getUser()).willReturn(booker);

        User payer = mock(User.class);
        given(payer.getId()).willReturn(1L);
        Payment payment = Payment.of(booking, payer);

        // when & then
        PaymentValidator paymentValidator = new PaymentValidator();
        paymentValidator.validatePayer(payment);
    }

    @DisplayName("예약자와 결제자가 다르면 예외가 발생한다.")
    @Test
    void validatePayerWhenPayerNotEqualsBooker() {
        // given
        Booking booking = mock(Booking.class);
        User booker = mock(User.class);
        given(booking.getUser()).willReturn(booker);
        given(booker.getId()).willReturn(1L);

        User payer = mock(User.class);
        given(payer.getId()).willReturn(2L);
        given(booker.doesNotEqual(payer)).willReturn(true);

        Payment payment = Payment.of(booking, payer);

        // when & then
        PaymentValidator paymentValidator = new PaymentValidator();
        assertThatThrownBy(() -> paymentValidator.validatePayer(payment))
                .isInstanceOf(RestApiException.class)
                .hasMessage(INVALID_PAYER.getMessage());
    }

    @DisplayName("예약의 예약시간이 만료되어 유효하지 않은 상태이면 예외가 발생한다.")
    @Test
    void validatePayableTime() {
        // given
        long allowedMinutes = ALLOWED_MINUTES.getMinutes();
        LocalDateTime bookingDateTime = LocalDateTime.of(2024, 8, 11, 11, 1);
        Booking booking = Booking.builder()
                .bookingDateTime(bookingDateTime)
                .build();
        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 11, 11, 10);
        Payment payment = Payment.of(booking, null, paymentDateTime);

        // when & then
        long passedMinutes = Duration.between(booking.getBookingDateTime(), paymentDateTime).toMinutes();
        Assertions.assertThat(passedMinutes).isGreaterThanOrEqualTo(allowedMinutes);

        PaymentValidator paymentValidator = new PaymentValidator();
        assertThatThrownBy(() -> paymentValidator.validatePayableTime(payment))
                .isInstanceOf(RestApiException.class)
                .hasMessage(PAYABLE_TIME_OVER.getMessage());
    }

    @DisplayName("결제자의 잔액이 결제액 보다 부족하면 예외가 발생한다.")
    @Test
    void validatePayability() {
        // given
        User payer = User.builder()
                .balance(10000)
                .build();

        int totalAmount = 30000;
        Booking booking = mock(Booking.class);
        given(booking.getTotalPrice()).willReturn(totalAmount);

        Payment payment = Payment.of(booking, payer);

        // when & then
        PaymentValidator paymentValidator = new PaymentValidator();

        Assertions.assertThat(payer.isBalanceLessThan(30000L)).isTrue();
        assertThatThrownBy(() -> paymentValidator.validatePayability(payment))
                        .isInstanceOf(RestApiException.class)
                        .hasMessage(BalanceErrorCode.NOT_ENOUGH_BALANCE.getMessage());
    }
}