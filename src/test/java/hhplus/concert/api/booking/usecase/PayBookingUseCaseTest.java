package hhplus.concert.api.booking.usecase;

import hhplus.concert.api.booking.controller.request.PaymentRequest;
import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.payment.components.PaymentHistoryWriter;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.support.PaymentService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PayBookingUseCaseTest {


    @Mock
    BookingReader bookingReader;

    @Mock
    PaymentHistoryWriter paymentHistoryWriter;

    @Mock
    PaymentService paymentService;

    @Mock
    ClockManager clockManager;

    @Mock
    UserReader userReader;

    @Mock
    BalanceHistoryWriter balanceHistoryWriter;

    @InjectMocks
    PayBookingUseCase payBookingUseCase;

    @DisplayName("예약을 결제한다.")
    @Test
    void execute() {
        // given
        Long bookingId = 1L;
        Long userId = 2L;
        PaymentRequest paymentRequest = new PaymentRequest(userId);

        Booking booking = mock(Booking.class);
        User payer = mock(User.class);
        LocalDateTime paymentDateTime = LocalDateTime.now();

        given(bookingReader.getBookingById(bookingId)).willReturn(booking);
        given(userReader.getUserById(userId)).willReturn(payer);
        given(clockManager.getNowDateTime()).willReturn(paymentDateTime);

        // when
        payBookingUseCase.execute(bookingId, paymentRequest);

        // then
        then(bookingReader).should(times(1)).getBookingById(bookingId);
        then(userReader).should(times(1)).getUserById(userId);
        then(clockManager).should(times(1)).getNowDateTime();
        then(paymentService).should(times(1)).pay(any(Payment.class));
        then(balanceHistoryWriter).should(times(1)).save(any(Balance.class));
        then(paymentHistoryWriter).should(times(1)).save(any(Payment.class));
        then(booking).should(times(1)).markAsComplete();
        then(booking).should(times(1)).markSeatsAsBooked();
    }
}