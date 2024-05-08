package hhplus.concert.distribution;

public enum TokenPolicy {
    MAX_CONCURRENT_USERS(50), MAX_WORKING_SEC(20);

    long value;

    private TokenPolicy(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
