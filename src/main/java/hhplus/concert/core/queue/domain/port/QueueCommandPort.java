package hhplus.concert.core.queue.domain.port;

import hhplus.concert.core.queue.domain.model.Queue;

import java.util.concurrent.TimeUnit;

public interface QueueCommandPort {

    void addTokenToSet(Queue queue);
    void removeTokenFromSet(Queue queue);
    void addTokenToSortedSet(Queue queue);
    void removeTokenFromSortedSet(Queue queue);
    void createTtlToken(Queue queue, long timeout, TimeUnit timeUnit);
}
