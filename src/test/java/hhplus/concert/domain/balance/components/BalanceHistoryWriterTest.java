package hhplus.concert.domain.balance.components;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.balance.repositories.BalanceHistoryWriterRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.domain.balance.models.BalanceHistory.createBalanceChargeHistory;
import static hhplus.concert.domain.balance.models.BalanceHistory.createBalanceUseHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BalanceHistoryWriterTest {

    @InjectMocks
    BalanceHistoryWriter balanceHistoryWriter;

    @Mock
    BalanceHistoryWriterRepository balanceHistoryWriterRepository;

    @DisplayName("인자값이 모두 유효하면, BalanceChargeHistory 데이터 저장에 성공한다.")
    @Test
    void saveBalanceChargeHistory_Success_ifWithValidArguments() {
        // given
        User user = User.builder().build();
        long amount = 10000L;
        BalanceHistory expected = createBalanceChargeHistory(user, amount);

        given(balanceHistoryWriterRepository.save(any(BalanceHistory.class))).willReturn(expected);

        // when
        BalanceHistory result = balanceHistoryWriter.saveBalanceChargeHistory(user, amount);

        // then
        assertThat(result).isEqualTo(expected);
        verify(balanceHistoryWriterRepository, times(1)).save(any(BalanceHistory.class));
    }

    @DisplayName("인자값이 모두 유효하면, BalanceUseHistory 데이터 저장에 성공한다.")
    @Test
    void saveBalanceUseHistory_Success_ifWithValidArguments() {
        // given
        User user = User.builder().build();
        long amount = 10000L;
        BalanceHistory expected = createBalanceUseHistory(user, amount);

        given(balanceHistoryWriterRepository.save(any(BalanceHistory.class))).willReturn(expected);

        // when
        BalanceHistory result = balanceHistoryWriter.saveBalanceUseHistory(user, amount);

        // then
        assertThat(result).isEqualTo(expected);
        verify(balanceHistoryWriterRepository, times(1)).save(any(BalanceHistory.class));
    }

    //TODO: 실패케이스 작성
}