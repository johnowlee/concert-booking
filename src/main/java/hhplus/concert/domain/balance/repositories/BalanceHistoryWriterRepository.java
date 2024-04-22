package hhplus.concert.domain.balance.repositories;

import hhplus.concert.domain.balance.models.BalanceHistory;

public interface BalanceHistoryWriterRepository {

    BalanceHistory save(BalanceHistory balanceHistory);
}
