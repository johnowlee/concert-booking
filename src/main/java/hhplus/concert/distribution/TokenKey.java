package hhplus.concert.distribution;

public enum TokenKeys {
    ACTIVE_KEY("active"), WAITING_KEY("waiting");

    String value;

    private TokenKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
