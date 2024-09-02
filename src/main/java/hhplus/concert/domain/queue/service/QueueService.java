package hhplus.concert.domain.queue.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.exception.code.TokenErrorCode.WAITING_TOKEN;

@Component
@RequiredArgsConstructor
public class QueueService {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;
    private final QueueMonitor queueMonitor;

    public Queue getQueueByToken(String token) {
        if (queueReader.isActiveToken(token)) {
            return Queue.createActiveQueue(token);
        }
        if (queueReader.isWaitingToken(token)) {
            return Queue.createWaitingQueue(token, getWaitingNumber(token));
        }
        throw new RestApiException(NOT_FOUND_TOKEN);
    }

    public Queue createNewQueue(String token, long score) {
        return isAccessible() ? createActiveQueue(token) : createWaitingQueue(token, score);
    }

    public void validateToken(String token) {
        if (queueReader.isWaitingToken(token)) throw new RestApiException(WAITING_TOKEN);
        if (queueReader.isNotActiveToken(token)) throw new RestApiException(NOT_FOUND_TOKEN);
    }

    private Queue createActiveQueue(String token) {
        Queue queue = Queue.createActiveQueue(token);
        queueWriter.addActiveToken(queue);
        queueWriter.createActiveKey(queue, queueMonitor);
        return queue;
    }

    private Queue createWaitingQueue(String token, long score) {
        Queue queue = Queue.createWaitingQueue(token, score);
        queueWriter.addWaitingToken(queue);
        return Queue.createWaitingQueue(queue.getToken(), getWaitingNumber(queue.getToken()));
    }

    private int getWaitingNumber(String token) {
        return queueReader.getWaitingNumber(token);
    }

    private boolean isAccessible() {
        Long currentActiveTokenCount = queueReader.getTokenCountFromSet(Key.ACTIVE);
        return currentActiveTokenCount == null || currentActiveTokenCount < queueMonitor.getMaxActiveUserCount();
    }
}
