package hhplus.concert.domain.history.payment.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hhplus.concert.api.exception.code.BookingErrorCode.INVALID_PAYER;
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
}