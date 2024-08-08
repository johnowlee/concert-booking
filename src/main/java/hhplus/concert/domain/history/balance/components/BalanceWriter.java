package hhplus.concert.domain.history.balance.components;

import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.repositories.BalanceWriterRepository;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.domain.history.balance.models.Balance.createBalanceChargeHistory;
import static hhplus.concert.domain.history.balance.models.Balance.createBalanceUseHistory;

@Component
@RequiredArgsConstructor
public class BalanceWriter {

    private final BalanceWriterRepository balanceWriterRepository;

    public Balance saveBalanceChargeHistory(User user, long amount) {
        return balanceWriterRepository.save(createBalanceChargeHistory(user, amount));
    }

    public Balance saveBalanceUseHistory(User user, long amount) {
        return balanceWriterRepository.save(createBalanceUseHistory(user, amount));
    }

}
