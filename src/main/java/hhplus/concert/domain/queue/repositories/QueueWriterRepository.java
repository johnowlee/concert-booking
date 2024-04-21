package hhplus.concert.domain.queue.repositories;

import hhplus.concert.domain.queue.model.Queue;

public interface QueueWriterRepository {

    public Queue save(Queue queue);
}
