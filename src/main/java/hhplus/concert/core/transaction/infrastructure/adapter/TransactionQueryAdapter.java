package hhplus.concert.core.transaction.infrastructure.adapter;

import hhplus.concert.core.transaction.domain.model.TransactionType;
import hhplus.concert.core.transaction.domain.port.TransactionQueryPort;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class TransactionQueryAdapter implements TransactionQueryPort {

    private final TransactionJpaRepository transactionJpaRepository;


    @Override
    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionJpaRepository.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
