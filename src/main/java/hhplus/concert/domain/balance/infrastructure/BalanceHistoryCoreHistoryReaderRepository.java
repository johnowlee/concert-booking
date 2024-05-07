package hhplus.concert.domain.balance.infrastructure;

import hhplus.concert.domain.balance.models.TransactionType;
import hhplus.concert.domain.balance.repositories.BalanceHistoryReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class BalanceHistoryCoreHistoryReaderRepository implements BalanceHistoryReaderRepository {

    private final BalanceHistoryJpaRepository balanceHistoryJpaRepository;


    @Override
    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return balanceHistoryJpaRepository.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
