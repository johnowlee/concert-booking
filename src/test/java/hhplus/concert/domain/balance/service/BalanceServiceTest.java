package hhplus.concert.domain.balance.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.balance.service.BalanceService;
import hhplus.concert.domain.history.payment.event.PaymentCompleteEvent;
import hhplus.concert.domain.support.event.EventPublisher;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @InjectMocks
    BalanceService balanceService;

    @Mock
    EventPublisher eventPublisher;

    @Mock
    BalanceHistoryWriter balanceHistoryWriter;

    @DisplayName("유저의 계좌에 잔액이 차감되고 이벤트발행, balance이력 저장에 성공한다.")
    @Test
    void use_ShouldSucceed() {
        // given
        User user = Mockito.mock(User.class);
        Booking booking = Mockito.mock(Booking.class);

        given(booking.getUser()).willReturn(user);
        given(booking.getTotalPrice()).willReturn(10000);

        // when
        balanceService.use(booking);

        // then
        then(user).should().useBalance(10000);
        then(eventPublisher).should().publish(any(PaymentCompleteEvent.class));
        then(balanceHistoryWriter).should().saveBalanceUseHistory(user, 10000);
    }

    @DisplayName("유저의 계좌에 잔액이 사용액 보다 부족하면 예외를 발생시킨다.")
    @Test
    void use_ShouldThrowException_WhenBalanceIsNotEnough() {
        // given
        User user = Mockito.mock(User.class);
        Booking booking = Mockito.mock(Booking.class);

        given(booking.getUser()).willReturn(user);
        given(booking.getTotalPrice()).willReturn(10000);

        willThrow(new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE))
                .given(user)
                .useBalance(10000);

        // when
        RestApiException exception = assertThrows(
                RestApiException.class,
                () -> balanceService.use(booking)
        );

        // then
        assertEquals(BalanceErrorCode.NOT_ENOUGH_BALANCE, exception.getErrorCode());
        then(user).should().useBalance(10000);
        then(eventPublisher).shouldHaveNoInteractions();
        then(balanceHistoryWriter).shouldHaveNoInteractions();

    }

    @DisplayName("사용금액이 음수이면 예외를 발생시킨다.")
    @Test
    void use_ShouldThrowException_WhenAmountIsNegative() {
        // given
        User user = Mockito.mock(User.class);
        Booking booking = Mockito.mock(Booking.class);

        given(booking.getUser()).willReturn(user);
        given(booking.getTotalPrice()).willReturn(-10);

        willThrow(new RestApiException(BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT))
                .given(user)
                .useBalance(-10);

        // when
        RestApiException exception = assertThrows(
                RestApiException.class,
                () -> balanceService.use(booking)
        );

        // then
        assertEquals(BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT, exception.getErrorCode());
        then(user).should().useBalance(-10);
        then(eventPublisher).shouldHaveNoInteractions();
        then(balanceHistoryWriter).shouldHaveNoInteractions();
    }
}