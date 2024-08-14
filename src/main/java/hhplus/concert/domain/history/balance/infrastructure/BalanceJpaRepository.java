package hhplus.concert.domain.history.balance.infrastructure;

import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BalanceJpaRepository extends JpaRepository<Balance, Long> {

    @Query("select sum(bh.amount) from Balance bh" +
            " where bh.user.id = :userId" +
            " and bh.transactionDateTime between :startDate and :endDate" +
            " and bh.transactionType = :transactionType ")
    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
