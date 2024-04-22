package hhplus.concert.domain.balance.infrastructure;

import hhplus.concert.domain.balance.models.BalanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceHistoryJpaRepository extends JpaRepository<BalanceHistory, Long> {
}
