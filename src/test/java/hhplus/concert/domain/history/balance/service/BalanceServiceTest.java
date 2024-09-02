package hhplus.concert.domain.history.balance.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private BalanceHistoryWriter balanceHistoryWriter;

    @Mock
    private EntityManager em;

    @InjectMocks
    private BalanceService balanceService;

    @Test
    void testChargeBalanceWithOptimisticLock_success() {
        // given
        User user = mock(User.class);
        Balance balance = mock(Balance.class);

        given(balance.getUser()).willReturn(user);

        // when
        balanceService.chargeBalanceWithOptimisticLock(balance);

        // then
        then(balanceHistoryWriter).should(times(1)).save(balance);
    }

    @Test
    void testChargeBalanceWithOptimisticLock_optimisticLockException() {
        // given
        User user = mock(User.class);
        Balance balance = mock(Balance.class);

        given(balance.getUser()).willReturn(user);
        doThrow(OptimisticLockException.class).when(em).flush();

        // when & then
        Assertions.assertThatThrownBy(() -> balanceService.chargeBalanceWithOptimisticLock(balance))
                .isInstanceOf(RestApiException.class)
                .hasMessage(BalanceErrorCode.FAILED_CHARGE.getMessage());
    }
}