package hhplus.concert.domain.history.balance.infrastructure;

import hhplus.concert.domain.history.balance.models.TransactionType;
import hhplus.concert.domain.history.balance.repositories.BalanceReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class BalanceCoreReaderRepository implements BalanceReaderRepository {

    private final BalanceJpaRepository balanceJpaRepository;


    @Override
    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return balanceJpaRepository.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
