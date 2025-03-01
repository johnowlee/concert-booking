package hhplus.concert.representer.api.queue;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.representer.api.queue.response.QueueResponse;
import org.springframework.stereotype.Component;

@Component
public class QueueControllerMapper {

    public QueueResponse toQueueResponse(Queue queue) {
        Integer waitingNumber = queue.getWaitingNumber() == 0 ? null : queue.getWaitingNumber();
        return new QueueResponse(queue.getToken(), queue.getKey().toString(), waitingNumber);
    }
}
