package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
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
    public Long getFirstPositionByStatus(QueueStatus status) {
        return queueJpaRepository.findFirstPositionByStatus(status).orElse(0L);
    }

    @Override
    public Long getLastPositionByStatus(QueueStatus status) {
        return queueJpaRepository.findLastPositionByStatus(status).orElse(0L);
    }

    @Override
    public Queue getQueueById(String id) {
        return queueJpaRepository.findById(id)
                .orElse(null);
    }
}
