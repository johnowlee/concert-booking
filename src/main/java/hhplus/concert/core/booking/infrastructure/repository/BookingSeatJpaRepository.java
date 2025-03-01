package hhplus.concert.core.booking.infrastructure.repository;

import hhplus.concert.core.booking.domain.model.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingSeatJpaRepository extends JpaRepository<BookingSeat, Long> {

    @Query("SELECT bs FROM BookingSeat bs WHERE bs.seat.id IN :seatIds")
    List<BookingSeat> findAllBySeatIds(List<Long> seatIds);
}
