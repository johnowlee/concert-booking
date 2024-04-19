package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {
}
