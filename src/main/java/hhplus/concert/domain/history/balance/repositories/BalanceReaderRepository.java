package hhplus.concert.domain.history.balance.repositories;

import hhplus.concert.domain.history.balance.models.TransactionType;

import java.time.LocalDateTime;

public interface BalanceReaderRepository {

    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
