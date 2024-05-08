package hhplus.concert.distribution;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenResponse(String token, String key, Long waitingNumber) {

    public static TokenResponse createActiveTokenResponse(String token, TokenKey tokenKey) {
        return new TokenResponse(token, tokenKey.getValue(), null);
    }

    public static TokenResponse createWaitingTokenResponse(String token, TokenKey tokenKey, Long waitingNumber) {
        return new TokenResponse(token, tokenKey.getValue(), waitingNumber);
    }
}
