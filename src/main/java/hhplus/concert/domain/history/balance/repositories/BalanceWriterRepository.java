package hhplus.concert.domain.history.balance.repositories;

import hhplus.concert.domain.history.balance.models.Balance;

public interface BalanceWriterRepository {

    Balance save(Balance balance);
}
