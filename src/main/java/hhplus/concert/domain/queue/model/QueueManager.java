package hhplus.concert.domain.queue.model;

public enum QueueManager {
    MAX_PROCESSABLE_COUNT(50), QUEUE_EXPIRY_MINUTES(10);

    int value;

    private QueueManager(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public long toLong() {
        return Long.parseLong(String.valueOf(this.getValue()));
    }
}
