package hhplus.concert.core.transaction.infrastructure.repository;

import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.model.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByUserId(Long userId, Pageable pageable);

    @Query("select sum(bh.amount) from Transaction bh" +
            " where bh.user.id = :userId" +
            " and bh.transactionDateTime between :startDate and :endDate" +
            " and bh.transactionType = :transactionType ")
    long getAmountBySearchParam(Long userId, TransactionType transactionType, LocalDateTime startDate, LocalDateTime endDate);
}
