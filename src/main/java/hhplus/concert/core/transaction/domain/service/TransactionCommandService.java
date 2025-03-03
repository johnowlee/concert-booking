package hhplus.concert.core.transaction.domain.service;

import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.port.TransactionCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionCommandService {

    private final TransactionCommandPort transactionCommandPort;

    public Transaction save(Transaction transaction) {
        return transactionCommandPort.save(transaction);
    }
}
