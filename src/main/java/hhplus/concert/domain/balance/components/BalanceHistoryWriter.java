package hhplus.concert.domain.balance.components;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.balance.models.TransactionType;
import hhplus.concert.domain.balance.repositories.BalanceHistoryWriterRepository;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.domain.balance.models.BalanceHistory.createBalanceHistory;
import static hhplus.concert.domain.balance.models.TransactionType.*;

@Component
@RequiredArgsConstructor
public class BalanceHistoryWriter {

    private final BalanceHistoryWriterRepository balanceHistoryWriterRepository;

    public BalanceHistory saveBalanceUseHistory(User user, long amount) {
        return balanceHistoryWriterRepository.save(createBalanceHistory(user, amount, USE));
    }
}
