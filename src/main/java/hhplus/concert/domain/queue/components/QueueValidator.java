package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.domain.queue.model.QueueManager.*;
import static hhplus.concert.domain.queue.model.QueueManager.QUEUE_EXPIRY_MINUTES;

@Component
@Slf4j
public class QueueValidator {

    public boolean isQueueValid(Queue queue) {
        return queue != null && !isExpired(queue);
    }

    public boolean isExpired(Queue queue) {
        log.info("duration={}", Duration.between(queue.getUpdateAt(), LocalDateTime.now()).toMinutes());
        log.info("QUEUE_EXPIRY_MINUTES={}", QUEUE_EXPIRY_MINUTES.toLong());
        log.info("isExpired={}", Duration.between(queue.getUpdateAt(), LocalDateTime.now()).toMinutes() > QUEUE_EXPIRY_MINUTES.toLong());
        return Duration.between(queue.getUpdateAt(), LocalDateTime.now()).toMinutes() > QUEUE_EXPIRY_MINUTES.toLong();
    }

    public boolean isQueueProcessable(List<Queue> queues) {
        return queues.stream()
                .filter(q -> !isExpired(q))
                .collect(Collectors.toList()).size() < MAX_PROCESSABLE_COUNT.getValue();

    }
}
