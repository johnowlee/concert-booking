package hhplus.concert.application.user;

import hhplus.concert.domain.booking.components.BookingReader;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.payment.event.PaymentCompletion;
import hhplus.concert.domain.history.payment.models.Payment;
import hhplus.concert.domain.history.payment.service.PaymentService;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

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
    PaymentService paymentService;

    @Mock
    ClockManager clockManager;

    @Mock
    UserReader userReader;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    PayBookingUseCase payBookingUseCase;

    @DisplayName("예약을 결제하고 결제 완료 이벤트를 발행한다.")
    @Test
    void execute() {
        // given
        Long bookingId = 1L;
        Long userId = 2L;

        Booking booking = mock(Booking.class);
        User payer = mock(User.class);
        LocalDateTime paymentDateTime = LocalDateTime.now();

        given(bookingReader.getBookingById(bookingId)).willReturn(booking);
        given(userReader.getUserById(userId)).willReturn(payer);
        given(clockManager.getNowDateTime()).willReturn(paymentDateTime);

        // when
        payBookingUseCase.execute(userId, bookingId);

        // then
        then(bookingReader).should(times(1)).getBookingById(bookingId);
        then(userReader).should(times(1)).getUserById(userId);
        then(clockManager).should(times(1)).getNowDateTime();
        then(paymentService).should(times(1)).pay(any(Payment.class));
        then(eventPublisher).should(times(1)).publishEvent(any(PaymentCompletion.class));
    }
}