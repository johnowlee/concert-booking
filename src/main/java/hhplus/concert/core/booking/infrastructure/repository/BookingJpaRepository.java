package hhplus.concert.core.booking.infrastructure.repository;

import hhplus.concert.core.booking.domain.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingJpaRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b" +
            " join fetch b.user u" +
            " where u.id = :userId")
    List<Booking> findBookingsByUserId(Long userId);

    @Query("select b from Booking b" +
            " join b.bookingSeats bs" +
            " join bs.seat s" +
            " where s.id in :ids")
    List<Booking> findBookingsBySeatIds(List<Long> ids);
}
