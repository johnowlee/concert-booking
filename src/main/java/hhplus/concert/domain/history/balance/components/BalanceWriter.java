package hhplus.concert.domain.history.balance.components;

import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.repositories.BalanceWriterRepository;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.domain.history.balance.models.Balance.createChargeBalance;

@Component
@RequiredArgsConstructor
public class BalanceWriter {

    private final BalanceWriterRepository balanceWriterRepository;

    public Balance saveBalance(Balance balance) {
        return balanceWriterRepository.save(balance);
    }

    public Balance saveUseBalance(Balance balance) {
        return balanceWriterRepository.save(balance);
    }

}
