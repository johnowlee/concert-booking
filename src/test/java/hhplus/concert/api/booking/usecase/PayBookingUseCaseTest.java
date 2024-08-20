package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.dto.request.PaymentRequest;
import hhplus.concert.api.common.response.PaymentResponse;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.balance.support.BalanceService;
import hhplus.concert.domain.history.payment.components.PaymentWriter;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentValidator;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PayBookingUseCaseTest {


    @Mock
    BookingReader bookingReader;

    @Mock
    PaymentWriter paymentWriter;

    @Mock
    BalanceService balanceService;

    @Mock
    ClockManager clockManager;

    @Mock
    UserReader userReader;

    @Mock
    PaymentValidator paymentValidator;

    @InjectMocks
    PayBookingUseCase payBookingUseCase;

    @DisplayName("예약을 결제한다.")
    @Test
    void execute() {
        // given
        Long bookingId = 1L;
        Long userId = 2L;
        PaymentRequest request = new PaymentRequest(userId);
        Booking booking = mock(Booking.class);
        User user = mock(User.class);
        LocalDateTime now = LocalDateTime.now();
        Payment payment = mock(Payment.class);

        given(bookingReader.getBookingById(bookingId)).willReturn(booking);
        given(clockManager.getNowDateTime()).willReturn(now);
        given(userReader.getUserById(userId)).willReturn(user);
        given(paymentWriter.save(booking, now)).willReturn(payment);

        given(payment.getId()).willReturn(1L);
        given(payment.getPaymentDateTime()).willReturn(now);
        given(payment.getPaymentAmount()).willReturn(100000);
        given(payment.getUser()).willReturn(user);
        given(payment.getBooking()).willReturn(booking);

        // when
        PaymentResponse response = payBookingUseCase.execute(bookingId, request);

        // then
        verify(booking).validateBookingDateTime(now);
        verify(paymentValidator).validatePayer(booking, user);
        verify(balanceService).use(booking);
        verify(paymentWriter).save(booking, now);
        verify(booking).markAsComplete();
        verify(booking).reserveAllSeats();

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.paymentDateTime()).isEqualTo(now);
        assertThat(response.paymentAmount()).isEqualTo(100000);
    }
}