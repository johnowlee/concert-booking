package hhplus.concert.domain.queue.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Queue {
    Key key;
    String token;
    Long waitingNumber;
    double score;

    public String getKeyName() {
        return key.toString();
    }

    @Builder
    private Queue(Key key, String token, Long waitingNumber, double score) {
        this.key = key;
        this.token = token;
        this.waitingNumber = waitingNumber;
        this.score = score;
    }

    public static Queue createNewQueueByKey(Key key) {
        return Queue.builder()
                .key(key)
                .token(UUID.randomUUID().toString())
                .build();
    }

    public static Queue createQueue(String token) {
        return Queue.builder()
                .token(token)
                .build();
    }

    public static Queue createActiveQueue(String token) {
        return Queue.builder()
                .key(Key.ACTIVE)
                .token(token)
                .build();
    }

    public static Queue createWaitingQueue(String token, Long waitingNumber) {
        return Queue.builder()
                .key(Key.WAITING)
                .token(token)
                .waitingNumber(waitingNumber)
                .score(System.currentTimeMillis())
                .build();
    }
}
