package hhplus.concert.domain.history.balance.repositories;

import hhplus.concert.domain.history.balance.models.BalanceHistory;

public interface BalanceHistoryWriterRepository {

    BalanceHistory save(BalanceHistory balanceHistory);
}
