package hhplus.concert.representer.api.transaction.response;

import hhplus.concert.core.transaction.domain.model.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponse(long id, long amount, TransactionType transactionType, LocalDateTime transactionDateTime) {
}
