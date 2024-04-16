package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.entities.concert.ConcertOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConcertOptionJpaRepository extends JpaRepository<ConcertOptionEntity, Long> {

    @Query("select co from ConcertOptionEntity co join fetch co.concertEntity c where co.id = :id")
    Optional<ConcertOptionEntity> findConcertOptionById(Long id);
}
