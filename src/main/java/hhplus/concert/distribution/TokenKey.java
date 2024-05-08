package hhplus.concert.distribution;

public enum TokenKey {
    ACTIVE_KEY("active"), WAITING_KEY("waiting");

    String value;

    private TokenKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
