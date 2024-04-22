package hhplus.concert.domain.balance.infrastructure;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.balance.repositories.BalanceHistoryWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceHistoryCoreHistoryWriterRepository implements BalanceHistoryWriterRepository {

    private final BalanceHistoryJpaRepository balanceHistoryJpaRepository;

    @Override
    public BalanceHistory save(BalanceHistory balanceHistory) {
        return balanceHistoryJpaRepository.save(balanceHistory);
    }
}
