package hhplus.concert.domain.history.balance.infrastructure;

import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.repositories.BalanceWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BalanceCoreWriterRepository implements BalanceWriterRepository {

    private final BalanceJpaRepository balanceJpaRepository;

    @Override
    public Balance save(Balance balance) {
        return balanceJpaRepository.save(balance);
    }
}
