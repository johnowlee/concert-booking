package hhplus.concert.core.transaction.infrastructure.adapter;

import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import hhplus.concert.core.transaction.domain.port.TransactionCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionCommandAdapter implements TransactionCommandPort {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionJpaRepository.save(transaction);
    }
}
