package hhplus.concert.domain.history.balance.components;

import hhplus.concert.domain.history.balance.models.BalanceHistory;
import hhplus.concert.domain.history.balance.repositories.BalanceHistoryWriterRepository;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.domain.history.balance.models.BalanceHistory.createBalanceChargeHistory;
import static hhplus.concert.domain.history.balance.models.BalanceHistory.createBalanceUseHistory;

@Component
@RequiredArgsConstructor
public class BalanceHistoryWriter {

    private final BalanceHistoryWriterRepository balanceHistoryWriterRepository;

    public BalanceHistory saveBalanceChargeHistory(User user, long amount) {
        return balanceHistoryWriterRepository.save(createBalanceChargeHistory(user, amount));
    }

    public BalanceHistory saveBalanceUseHistory(User user, long amount) {
        return balanceHistoryWriterRepository.save(createBalanceUseHistory(user, amount));
    }

}
