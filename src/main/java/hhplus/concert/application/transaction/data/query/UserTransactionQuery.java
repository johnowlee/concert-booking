package hhplus.concert.application.transaction.data.query;

import org.springframework.data.domain.Pageable;

public record UserTransactionQuery(long userId, Pageable pageable) {
}
