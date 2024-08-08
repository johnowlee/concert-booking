package hhplus.concert.domain.balance.components;

import hhplus.concert.domain.history.balance.components.BalanceWriter;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.repositories.BalanceWriterRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.domain.history.balance.models.Balance.createBalanceChargeHistory;
import static hhplus.concert.domain.history.balance.models.Balance.createBalanceUseHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BalanceWriterTest {

    @InjectMocks
    BalanceWriter balanceWriter;

    @Mock
    BalanceWriterRepository balanceWriterRepository;

    @DisplayName("인자값이 모두 유효하면, BalanceChargeHistory 데이터 저장에 성공한다.")
    @Test
    void saveBalanceChargeHistory_Success_ifWithValidArguments() {
        // given
        User user = User.builder().build();
        long amount = 10000L;
        Balance expected = createBalanceChargeHistory(user, amount);

        given(balanceWriterRepository.save(any(Balance.class))).willReturn(expected);

        // when
        Balance result = balanceWriter.saveBalanceChargeHistory(user, amount);

        // then
        assertThat(result).isEqualTo(expected);
        verify(balanceWriterRepository, times(1)).save(any(Balance.class));
    }

    @DisplayName("인자값이 모두 유효하면, BalanceUseHistory 데이터 저장에 성공한다.")
    @Test
    void saveBalanceUseHistory_Success_ifWithValidArguments() {
        // given
        User user = User.builder().build();
        long amount = 10000L;
        Balance expected = createBalanceUseHistory(user, amount);

        given(balanceWriterRepository.save(any(Balance.class))).willReturn(expected);

        // when
        Balance result = balanceWriter.saveBalanceUseHistory(user, amount);

        // then
        assertThat(result).isEqualTo(expected);
        verify(balanceWriterRepository, times(1)).save(any(Balance.class));
    }

    //TODO: 실패케이스 작성
}