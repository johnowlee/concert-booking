package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueCoreReaderRepository implements QueueReaderRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue getProcessingQueueByUserId(Long userId) {
        return queueJpaRepository.findLastProcessingQueueByUserId(userId, QueueStatus.PROCESSING)
                .orElse(null);
    }

    @Override
    public List<Queue> getQueuesByStatusAndPosition(QueueStatus status, long position) {
        return queueJpaRepository.findAllByStatusAndPosition(status, position);
    }

    @Override
    public long getFirstPositionByStatus(QueueStatus status) {
        return queueJpaRepository.findFirstPositionByStatus(status);
    }

    @Override
    public long getLastPositionByStatus(QueueStatus status) {
        return queueJpaRepository.findLastPositionByStatus(status);
    }
}
