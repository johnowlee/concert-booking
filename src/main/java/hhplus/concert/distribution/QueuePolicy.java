package hhplus.concert.distribution;

public enum QueuePolicy {
    MAX_CONCURRENT_USERS(50), MAX_WORKING_SEC(600);

    long value;

    private QueuePolicy(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
