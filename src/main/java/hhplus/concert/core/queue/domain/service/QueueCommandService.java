package hhplus.concert.core.queue.domain.service;

import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.domain.port.QueueCommandPort;
import hhplus.concert.core.queue.domain.service.support.monitor.Ttl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueCommandService {

    private final QueueCommandPort queueCommandPort;

    public void addActiveToken(Queue queue) {
        queueCommandPort.addTokenToSet(queue);
    }

    public void removeActiveToken(Queue queue) {
        queueCommandPort.removeTokenFromSet(queue);
    }

    public void addWaitingToken(Queue queue) {
        queueCommandPort.addTokenToSortedSet(queue);
    }

    public void removeWaitingToken(Queue queue) {
        queueCommandPort.removeTokenFromSortedSet(queue);
    }

    public void setActiveTokenTtl(Queue queue, Ttl ttl) {
        queueCommandPort.createTtlToken(queue, ttl.timeout(), ttl.timeUnit());
    }
}
