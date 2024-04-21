package hhplus.concert.domain.queue.repositories;

import hhplus.concert.domain.queue.model.Queue;

public interface QueueReaderRepository {

    public Queue getQueueByUserId(Long userId);
}
