package hhplus.concert.application.transaction.usecase;

import hhplus.concert.application.transaction.data.query.UserTransactionQuery;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.service.TransactionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindUserTransactionsUseCase {

    private final TransactionQueryService transactionQueryService;

    public Page<Transaction> execute(UserTransactionQuery query) {
        return transactionQueryService.findAllByUserId(query.userId(), query.pageable());
    }
}
