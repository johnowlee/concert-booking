package hhplus.concert.api.queue.dto.response;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;

public record QueueResponse(String queueId, QueueStatus queueStatus, Long position) {
    public static QueueResponse from(Queue queue) {
        return new QueueResponse(queue.getId(), queue.getStatus(), queue.getPosition());
    }
}
