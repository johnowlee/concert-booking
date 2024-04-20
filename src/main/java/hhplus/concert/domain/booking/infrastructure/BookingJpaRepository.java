package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingJpaRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b" +
            " join fetch b.user u" +
            " left join fetch b.payment p" +
            " where u.id = :userId")
    List<Booking> findBookingsByUserId(Long userId);

    @Query("select b from Booking b" +
            " join fetch b.user u")
    Booking findBookingById(Long userId);
}
