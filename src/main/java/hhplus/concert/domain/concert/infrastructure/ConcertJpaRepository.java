package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
}
