package hhplus.concert.domain.queue.model;

import lombok.Getter;

@Getter
public class Queue {
    Key key;
    String token;
    long waitingNumber;
    double score;

    public String getKeyName() {
        return key.toString();
    }

    private Queue(Key key, String token, long waitingNumber, double score) {
        this.key = key;
        this.token = token;
        this.waitingNumber = waitingNumber;
        this.score = score;
    }

    public static Queue createActiveQueue(String token) {
        return new Queue(Key.ACTIVE, token, 0, 0);
    }

    public static Queue createWaitingQueue(String token) {
        return new Queue(Key.WAITING, token, 0, System.currentTimeMillis());
    }
}
