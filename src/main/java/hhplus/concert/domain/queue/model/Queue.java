package hhplus.concert.domain.queue.model;

import lombok.Builder;
import lombok.Getter;

import static hhplus.concert.domain.queue.model.Key.ACTIVE;
import static hhplus.concert.domain.queue.model.Key.WAITING;

@Getter
public class Queue {
    private Key key;
    private String token;
    private Long waitingNumber;
    private long score;

    public String getKeyName() {
        return key.toString();
    }

    @Builder
    private Queue(Key key, String token, Long waitingNumber, long score) {
        this.key = key;
        this.token = token;
        this.waitingNumber = waitingNumber;
        this.score = score;
    }

    private static Queue of(Key key, String token, Long waitingNumber, long score) {
        return builder()
                .key(key)
                .token(token)
                .waitingNumber(waitingNumber)
                .score(score)
                .build();
    }

    public static Queue createActiveQueue(String token) {
        return of(ACTIVE, token, null, 0);
    }

    public static Queue createWaitingQueue(String token) {
        return of(WAITING, token, null, 0);
    }

    public static Queue createWaitingQueue(String token, Long waitingNumber) {
        return of(WAITING, token, waitingNumber, 0);
    }

    public static Queue createNewWaitingQueue(String token, long score) {
        return of(WAITING, token, null, score);
    }
}
