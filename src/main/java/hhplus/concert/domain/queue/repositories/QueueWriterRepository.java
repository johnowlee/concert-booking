package hhplus.concert.domain.queue.repositories;

import hhplus.concert.domain.queue.model.Queue;

import java.util.concurrent.TimeUnit;

public interface QueueWriterRepository {

    void addUserToActiveSet(Queue queue);
    void removeUserFromActiveSet(Queue queue);

    void addUserToWaitingSortedSet(Queue queue);
    void removeUserFromWaitingSortedSet(Queue queue);

    void createUserTimeout(Queue queue, long timeout, TimeUnit timeUnit);
}
