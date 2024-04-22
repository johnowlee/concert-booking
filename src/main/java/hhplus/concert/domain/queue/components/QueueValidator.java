package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.domain.queue.model.QueueManager.MAX_PROCESSABLE_COUNT;
import static hhplus.concert.domain.queue.model.QueueManager.QUEUE_EXPIRY_MINUTES;

@Component
public class QueueValidator {

    public boolean isQueueValid(Queue queue) {
        return queue != null && !isExpired(queue);
    }

    public boolean isExpired(Queue queue) {
        return Duration.between(queue.getUpdateAt(), LocalDateTime.now()).toMinutes() > QUEUE_EXPIRY_MINUTES.toLong();
    }

    public boolean isQueueProcessable(List<Queue> queues) {
        return queues.stream()
                .filter(q -> !isExpired(q))
                .collect(Collectors.toList()).size() < MAX_PROCESSABLE_COUNT.getValue();
    }
}
