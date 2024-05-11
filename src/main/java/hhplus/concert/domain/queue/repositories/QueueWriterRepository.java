package hhplus.concert.domain.queue.repositories;

import hhplus.concert.domain.queue.model.Queue;

import java.util.concurrent.TimeUnit;

public interface QueueWriterRepository {

    void addTokenToSet(Queue queue);
    void removeTokenFromSet(Queue queue);

    void addTokenToSortedSet(Queue queue);
    void removeTokenFromSortedSet(Queue queue);

    void createTimeoutKey(Queue queue, long timeout, TimeUnit timeUnit);
}
