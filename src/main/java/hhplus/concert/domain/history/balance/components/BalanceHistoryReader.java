package hhplus.concert.domain.history.balance.components;

import hhplus.concert.domain.history.balance.models.TransactionType;
import hhplus.concert.domain.history.balance.repositories.BalanceReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BalanceHistoryReader {

    private final BalanceReaderRepository balanceReaderRepository;

    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return balanceReaderRepository.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
