package hhplus.concert.domain.history.balance.components;

import hhplus.concert.domain.history.balance.components.BalanceReader;
import hhplus.concert.domain.history.balance.models.TransactionType;
import hhplus.concert.domain.history.balance.repositories.BalanceReaderRepository;
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
class BalanceReaderTest {

    @InjectMocks
    BalanceReader balanceReader;

    @Mock
    BalanceReaderRepository balanceReaderRepository;

    @Test
    void GetAmountBySearchParam_Success() {
        // given
        long expectedAmount = 1000L;
        given(balanceReaderRepository.getAmountBySearchParam(anyLong(), any(TransactionType.class), any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(expectedAmount);

        // when
        long result = balanceReader.getAmountBySearchParam(1L, TransactionType.USE, LocalDateTime.now().minusDays(10), LocalDateTime.now());

        // then
        assertEquals(expectedAmount, result);
        then(balanceReaderRepository).should(times(1)).getAmountBySearchParam(anyLong(), any(TransactionType.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }

}