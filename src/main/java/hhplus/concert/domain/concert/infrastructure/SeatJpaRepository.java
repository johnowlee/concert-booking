package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Seat> findAllById(Long id);
}
