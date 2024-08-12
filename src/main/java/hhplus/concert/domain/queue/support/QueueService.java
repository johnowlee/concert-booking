package hhplus.concert.domain.queue.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.manager.TtlManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;

@Component
@RequiredArgsConstructor
public class QueueService {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;
    private final TtlManager ttlManager;

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
        return queueReader.isAccessible() ? createActiveQueue(token) : createWaitingQueue(token, score);
    }

    private Queue createActiveQueue(String token) {
        Queue queue = Queue.createActiveQueue(token);
        queueWriter.addActiveToken(queue);
        queueWriter.createActiveKey(queue, ttlManager);
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
}
