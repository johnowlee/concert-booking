package hhplus.concert.distribution;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Token(String token, String key, @JsonIgnore Long waitingNumber) {

    public static Token createActiveToken(String token, TokenKey tokenKey) {
        return new Token(token, tokenKey.getValue(), null);
    }

    public static Token createWaitingToken(String token, TokenKey tokenKey, Long waitingNumber) {
        return new Token(token, tokenKey.getValue(), waitingNumber);
    }
}
