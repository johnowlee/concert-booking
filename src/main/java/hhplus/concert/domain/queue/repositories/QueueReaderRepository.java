package hhplus.concert.domain.queue.repositories;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;

import java.util.List;

public interface QueueReaderRepository {

    Queue getProcessingQueueByUserId(Long userId);

    List<Queue> getQueuesByStatusAndPosition(QueueStatus status, long position);

    long getFirstPositionByStatus(QueueStatus status);

    long getLastPositionByStatus(QueueStatus status);
}
