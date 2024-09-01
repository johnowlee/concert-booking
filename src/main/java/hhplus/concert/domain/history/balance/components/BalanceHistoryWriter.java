package hhplus.concert.domain.history.balance.components;

import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.repositories.BalanceWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BalanceHistoryWriter {

    private final BalanceWriterRepository balanceWriterRepository;

    public Balance saveBalance(Balance balance) {
        return balanceWriterRepository.save(balance);
    }
}
