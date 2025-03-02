package hhplus.concert.core.transaction.infrastructure.adapter;

import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.model.TransactionType;
import hhplus.concert.core.transaction.domain.port.TransactionQueryPort;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class TransactionQueryAdapter implements TransactionQueryPort {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Page<Transaction> findAllByUserId(Long userId, Pageable pageable) {
        return transactionJpaRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionJpaRepository.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
