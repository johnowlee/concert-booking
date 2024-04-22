package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<Queue, String> {

    @Query("select q from Queue q" +
            " join fetch q.user u " +
            " where u.id = :userId" +
            " and q.status = :status" +
            " order by q.updateAt desc" +
            " limit 1")
    Optional<Queue> findLastProcessingQueueByUserId(Long userId, QueueStatus status);

    List<Queue> findAllByStatusAndPosition(QueueStatus status, long position);

    @Query("select q.position from Queue q where q.status = :status order by q.position asc limit 1")
    Optional<Long> findFirstPositionByStatus(QueueStatus status);

    @Query("select q.position from Queue q where q.status = :status order by q.position desc limit 1")
    Optional<Long> findLastPositionByStatus(QueueStatus status);

    @Query("select q from Queue q join fetch q.user where q.id = :id")
    Optional<Queue> findById(String id);
}
