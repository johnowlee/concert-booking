package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.entities.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, Long> {

    @Query("select b from BookingEntity b join b.userEntity u where u.id = :userId")
    List<BookingEntity> findBookingsByUserId(Long userId);
}
