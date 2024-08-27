package hhplus.concert.domain.history.payment.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
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

        given(booking.getUser()).willReturn(booker);

        PaymentValidator paymentValidator = new PaymentValidator();

        // when & then
        paymentValidator.validatePayer(booking, booker);
    }

    @DisplayName("예약자와 결제자가 다르면 예외가 발생한다.")
    @Test
    void validatePayerWhenPayerNotEqualsBooker() {
        // given
        Booking booking = mock(Booking.class);
        User payer = mock(User.class);
        User booker = mock(User.class);

        given(booking.getUser()).willReturn(booker);
        given(booker.getId()).willReturn(1L);
        given(payer.getId()).willReturn(2L);

        given(booker.doesNotEqual(payer)).willReturn(true);

        PaymentValidator paymentValidator = new PaymentValidator();

        // when & then
        assertThatThrownBy(() -> paymentValidator.validatePayer(booking, payer))
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

        // when & then
        LocalDateTime verificationTime = LocalDateTime.of(2024, 8, 11, 11, 10);
        long passedMinutes = Duration.between(booking.getBookingDateTime(), verificationTime).toMinutes();
        Assertions.assertThat(passedMinutes).isGreaterThanOrEqualTo(allowedMinutes);

        PaymentValidator paymentValidator = new PaymentValidator();
        assertThatThrownBy(() -> paymentValidator.validatePayableTime(booking, verificationTime))
                .isInstanceOf(RestApiException.class)
                .hasMessage(PAYABLE_TIME_OVER.getMessage());
    }
}