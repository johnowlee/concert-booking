package hhplus.concert.domain.balance.repositories;

import hhplus.concert.domain.balance.models.TransactionType;

import java.time.LocalDateTime;

public interface BalanceHistoryReaderRepository {

    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
