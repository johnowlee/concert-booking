package hhplus.concert.api.queue.dto.request;

public record QueueTokenRequest(String token) {
    public static QueueTokenRequest from(String token) {
        return new QueueTokenRequest(token);
    }
}
