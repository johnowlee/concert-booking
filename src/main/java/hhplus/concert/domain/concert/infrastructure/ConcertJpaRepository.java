package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.entities.concert.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}
