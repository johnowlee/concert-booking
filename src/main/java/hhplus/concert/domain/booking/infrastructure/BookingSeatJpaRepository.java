package hhplus.concert.domain.booking.infrastructure;

import hhplus.concert.domain.booking.models.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatJpaRepository extends JpaRepository<BookingSeat, Long> {
}
