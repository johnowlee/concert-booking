package hhplus.concert.core.transaction.domain.port;

import hhplus.concert.core.transaction.domain.model.TransactionType;

import java.time.LocalDateTime;

public interface TransactionQueryPort {

    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
