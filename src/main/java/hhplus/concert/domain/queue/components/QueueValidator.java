package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.domain.queue.model.QueueManager.MAX_PROCESSABLE_COUNT;

@Component
public class QueueValidator {

    public boolean isQueueProcessable(List<Queue> queues) {
        return queues.stream()
                .filter(q -> !q.isExpired())
                .collect(Collectors.toList()).size() < MAX_PROCESSABLE_COUNT.getValue();
    }
}
