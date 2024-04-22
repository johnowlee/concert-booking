package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class QueueReader {

    private final QueueReaderRepository queueReaderRepository;

    public Queue getProcessingQueueByUserId(Long userId) {
        return queueReaderRepository.getProcessingQueueByUserId(userId);
    }

    public List<Queue> getProcessingQueues() {
        return queueReaderRepository.getQueuesByStatusAndPosition(QueueStatus.PROCESSING, 0);
    }

    public long getLastWaitingPosition() {
        return queueReaderRepository.getLastPositionByStatus(QueueStatus.WAITING);
    }

    public long getRealWaitingPosition(long waitingPosition) {
            if (getLastWaitingPosition() == waitingPosition) {
                return 1;
            }
        return waitingPosition - queueReaderRepository.getFirstPositionByStatus(QueueStatus.WAITING) + 1;
    }

    public Queue getQueueById(String id) {
        return queueReaderRepository.getQueueById(id);
    }
}
