package hhplus.concert.core.transaction.domain.port;

import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TransactionQueryPort {

    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
