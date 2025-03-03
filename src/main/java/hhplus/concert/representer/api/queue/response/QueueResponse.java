package hhplus.concert.representer.api.queue.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QueueResponse(String token, String key, Integer waitingNumber) {

}
