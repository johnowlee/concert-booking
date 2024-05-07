package hhplus.concert.domain.balance.infrastructure;

import hhplus.concert.domain.balance.models.BalanceHistory;
import hhplus.concert.domain.balance.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BalanceHistoryJpaRepository extends JpaRepository<BalanceHistory, Long> {

    @Query("select sum(bh.amount) from BalanceHistory bh" +
            " where bh.user.id = :userId" +
            " and bh.transactionDateTime between :startDate and :endDate" +
            " and bh.transactionType = :transactionType ")
    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
