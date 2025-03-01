package hhplus.concert.core.transaction.domain.port;

import hhplus.concert.core.transaction.domain.model.Transaction;

public interface TransactionCommandPort {

    Transaction save(Transaction transaction);
}
