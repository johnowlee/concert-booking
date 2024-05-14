package hhplus.concert.api.queue.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.queue.model.Key;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QueueResponse(String token, String key, Long waitingNumber) {

    public static QueueResponse createActiveQueueResponse(String token) {
        return new QueueResponse(token, Key.ACTIVE.toString(), null);
    }

    public static QueueResponse createWaitingQueueResponse(String token, Long waitingNumber) {
        return new QueueResponse(token, Key.WAITING.toString(), waitingNumber);
    }
}
