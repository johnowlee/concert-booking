package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueReader {

    private final QueueReaderRepository queueReaderRepository;

    public Queue getProcessingQueueByUserId(Long userId) {
        return queueReaderRepository.getProcessingQueueByUserId(userId);
    }

    public List<Queue> getProcessingQueues() {
        return queueReaderRepository.getQueuesByStatusAndPosition(QueueStatus.PROCESSING, 0);
    }

    public long getWaitingPosition() {
        return queueReaderRepository.getLastPositionByStatus(QueueStatus.WAITING) - queueReaderRepository.getFirstPositionByStatus(QueueStatus.WAITING);
    }
}
