package hhplus.concert.domain.queue.model;

import lombok.Builder;
import lombok.Getter;

import static hhplus.concert.domain.queue.model.Key.ACTIVE;
import static hhplus.concert.domain.queue.model.Key.WAITING;

@Getter
public class Queue {
    private Key key;
    private String token;
    private int waitingNumber;
    private double score;

    public String getKeyName() {
        return key.getKeyName();
    }

    @Builder
    private Queue(Key key, String token, int waitingNumber, double score) {
        this.key = key;
        this.token = token;
        this.waitingNumber = waitingNumber;
        this.score = score;
    }

    private static Queue of(Key key, String token, int waitingNumber, long score) {
        return builder()
                .key(key)
                .token(token)
                .waitingNumber(waitingNumber)
                .score((double) score)
                .build();
    }

    public static Queue createActiveQueue(String token) {
        return of(ACTIVE, token, 0, 0);
    }

    public static Queue createWaitingQueue(String token) {
        return of(WAITING, token, 0, 0);
    }

    public static Queue createWaitingQueue(String token, int waitingNumber) {
        return of(WAITING, token, waitingNumber, 0);
    }

    public static Queue createWaitingQueue(String token, long score) {
        return of(WAITING, token, 0, score);
    }
}
