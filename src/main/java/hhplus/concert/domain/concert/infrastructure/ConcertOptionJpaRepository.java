package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.domain.concert.models.ConcertOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConcertOptionJpaRepository extends JpaRepository<ConcertOption, Long> {

    @Query("select co from ConcertOption co join fetch co.concert c where co.id = :id")
    Optional<ConcertOption> findConcertOptionById(Long id);

    List<ConcertOption> findAllByConcertId(Long concertId);
}
