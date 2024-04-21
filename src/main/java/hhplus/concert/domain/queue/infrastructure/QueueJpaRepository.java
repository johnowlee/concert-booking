package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {

    @Query("select q from Queue q" +
            " join fetch q.user u " +
            " where u.id = :userId" +
            " and q.status = :status" +
            " order by q.updateAt desc" +
            " limit 1")
    Optional<Queue> findLastProcessingQueueByUserId(Long userId, QueueStatus status);

    List<Queue> findAllByStatusAndPosition(QueueStatus status, long position);

    @Query("select min(q.position) from Queue q where q.status = :status group by q.position")
    long findFirstPositionByStatus(QueueStatus status);

    @Query("select max(q.position) from Queue q where q.status = :status group by q.position")
    long findLastPositionByStatus(QueueStatus status);
}
