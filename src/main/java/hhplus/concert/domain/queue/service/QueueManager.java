package hhplus.concert.domain.queue.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;

@Component
@RequiredArgsConstructor
public class QueueManager {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;

    public Queue getQueueByToken(String token) {
        if (queueReader.isActiveToken(token)) {
            return Queue.createActiveQueue(token);
        }
        if (queueReader.isWaitingToken(token)) {
            Long waitingNumber = queueReader.getWaitingNumber(token);
            return Queue.createWaitingQueue(token, waitingNumber);
        }
        throw new RestApiException(NOT_FOUND_TOKEN);
    }

    public Queue createNewQueue() {
        if (queueReader.isAccessible()) {
            return createActiveQueue();
        }
        return createWaitingQueue();
    }

    private Queue createWaitingQueue() {
        Queue queue = Queue.createNewQueueByKey(Key.WAITING);
        queueWriter.addWaitingToken(queue);
        Long waitingNumber = queueReader.getWaitingNumber(queue.getToken());
        return Queue.createWaitingQueue(queue.getToken(), waitingNumber);
    }

    private Queue createActiveQueue() {
        Queue queue = Queue.createNewQueueByKey(Key.ACTIVE);
        queueWriter.addActiveToken(queue);
        queueWriter.createActiveKey(queue);
        return queue;
    }
}
