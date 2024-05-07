package hhplus.concert.domain.balance.components;

import hhplus.concert.domain.balance.models.TransactionType;
import hhplus.concert.domain.balance.repositories.BalanceHistoryReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BalanceHistoryReader {

    private final BalanceHistoryReaderRepository balanceHistoryReaderRepository;

    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return balanceHistoryReaderRepository.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
