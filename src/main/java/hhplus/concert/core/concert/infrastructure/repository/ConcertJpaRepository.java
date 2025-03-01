package hhplus.concert.core.concert.infrastructure.repository;

import hhplus.concert.core.concert.domain.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {
}
