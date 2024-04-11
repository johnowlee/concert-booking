package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}
