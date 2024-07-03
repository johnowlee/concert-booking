package hhplus.concert.domain.balance.components;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.balance.models.TransactionType;
import hhplus.concert.domain.balance.repositories.BalanceHistoryWriterRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.domain.balance.models.BalanceHistory.createBalanceHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceHistoryWriterTest {

    @InjectMocks
    BalanceHistoryWriter balanceHistoryWriter;

    @Mock
    BalanceHistoryWriterRepository balanceHistoryWriterRepository;



    @DisplayName("인자값이 모두 유효하면, BalanceHistory 데이터 저장에 성공한다.")
    @Test
    void saveBalanceHistory_Success_ifWithValidArguments() {
        // given
        User user = User.builder().build();
        long amount = 10000L;
        TransactionType transactionType = TransactionType.CHARGE;
        BalanceHistory expectedBalanceHistory = createBalanceHistory(user, amount, transactionType);

        given(balanceHistoryWriterRepository.save(any(BalanceHistory.class))).willReturn(expectedBalanceHistory);

        // when
        BalanceHistory result = balanceHistoryWriter.saveBalanceHistory(user, amount, transactionType);

        // then
        assertThat(result).isEqualTo(expectedBalanceHistory);
        verify(balanceHistoryWriterRepository, times(1)).save(any(BalanceHistory.class));
    }

    //TODO: 실패케이스 작성
}