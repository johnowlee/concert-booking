package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingSeatJpaRepository extends JpaRepository<BookingSeat, Long> {

    @Query("SELECT bs FROM BookingSeat bs WHERE bs.seat.id IN :seatIds")
    List<BookingSeat> findAllBySeatIds(List<Long> seatIds);
}
