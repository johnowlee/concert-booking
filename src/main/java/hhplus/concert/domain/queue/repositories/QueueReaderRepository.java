package hhplus.concert.domain.queue.repositories;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;

import java.util.List;

public interface QueueReaderRepository {

    Queue getProcessingQueueByUserId(Long userId);

    List<Queue> getQueuesByStatusAndPosition(QueueStatus status, long position);

    Long getFirstPositionByStatus(QueueStatus status);

    Long getLastPositionByStatus(QueueStatus status);

    Queue getQueueById(String id);
}
