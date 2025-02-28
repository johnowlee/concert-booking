package hhplus.concert.representer.api.queue.controller.request;

public record QueueTokenRequest(String token) {
    public static QueueTokenRequest from(String token) {
        return new QueueTokenRequest(token);
    }
}
