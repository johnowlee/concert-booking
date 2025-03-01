package hhplus.concert.core.transaction.domain.service;

import hhplus.concert.core.transaction.domain.model.TransactionType;
import hhplus.concert.core.transaction.domain.port.TransactionQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionQueryService {

    private final TransactionQueryPort transactionQueryPort;

    public long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionQueryPort.getAmountBySearchParam(userId, transactionType, startDate, endDate);
    }
}
