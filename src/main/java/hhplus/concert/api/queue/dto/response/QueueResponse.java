package hhplus.concert.api.queue.dto.response;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;

public record QueueResponse(String queueId, QueueStatus queueStatus, Long position) {
    public static QueueResponse of(Queue queue, long realWaitingPosition) {
        return new QueueResponse(queue.getId(), queue.getStatus(), realWaitingPosition);
    }
}
