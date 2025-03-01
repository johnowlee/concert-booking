package hhplus.concert.core.concert.infrastructure.repository;

import hhplus.concert.core.concert.domain.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.id in :ids")
    List<Seat> findAllByIdsWithPessimisticWriteLock(List<Long> ids);

    List<Seat> findAllByConcertOptionId(Long concertOptionId);
}
