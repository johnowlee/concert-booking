package hhplus.concert.api.queue.usecase.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.queue.model.Queue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QueueResponse(String token, String key, Integer waitingNumber) {

    public static QueueResponse createQueueResponse(Queue queue) {
        Integer waitingNumber = queue.getWaitingNumber() == 0 ? null : queue.getWaitingNumber();
        return new QueueResponse(queue.getToken(), queue.getKey().toString(), waitingNumber);
    }
}
