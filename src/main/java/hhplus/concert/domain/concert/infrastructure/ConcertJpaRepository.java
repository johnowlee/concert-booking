package hhplus.concert.domain.concert.infrastructure;

import hhplus.concert.entities.concert.ConcertEntity;
import hhplus.concert.entities.concert.ConcertOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {

    @Query("select c from ConcertEntity c join fetch c.concertOptionEntities ")
    List<ConcertEntity> findAll();

    @Query("select c from ConcertOptionEntity c join fetch c.concertEntity ")
    List<ConcertOptionEntity> findAllConcertOptions();
}
