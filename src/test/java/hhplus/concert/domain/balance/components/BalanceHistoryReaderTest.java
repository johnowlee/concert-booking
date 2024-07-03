package hhplus.concert.domain.balance.components;

import hhplus.concert.domain.balance.models.TransactionType;
import hhplus.concert.domain.balance.repositories.BalanceHistoryReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BalanceHistoryReaderTest {

    @InjectMocks
    BalanceHistoryReader balanceHistoryReader;

    @Mock
    BalanceHistoryReaderRepository balanceHistoryReaderRepository;

    @Test
    void GetAmountBySearchParam_Success() {
        // given
        long expectedAmount = 1000L;
        given(balanceHistoryReaderRepository.getAmountBySearchParam(anyLong(), any(TransactionType.class), any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(expectedAmount);

        // when
        long result = balanceHistoryReader.getAmountBySearchParam(1L, TransactionType.USE, LocalDateTime.now().minusDays(10), LocalDateTime.now());

        // then
        assertEquals(expectedAmount, result);
        then(balanceHistoryReaderRepository).should(times(1)).getAmountBySearchParam(anyLong(), any(TransactionType.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }

}