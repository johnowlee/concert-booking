package hhplus.concert.representer.api.transaction;

import hhplus.concert.application.transaction.data.query.UserTransactionQuery;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.representer.api.transaction.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionControllerMapper {
    public UserTransactionQuery toTransactionsByUserIdQuery(Long userId, Pageable pageable) {
        return new UserTransactionQuery(userId, pageable);
    }

    public List<TransactionResponse> toTransactionResponseList(Page<Transaction> transactions) {
        return transactions.getContent().stream()
                .map(this::toTransactionResponse)
                .toList();
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return new TransactionResponse(transaction.getId(), transaction.getAmount(), transaction.getTransactionType(), transaction.getTransactionDateTime());
    }
}
