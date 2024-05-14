package hhplus.concert.api.queue.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.queue.model.Key;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenResponse(String token, String key, Long waitingNumber) {

    public static TokenResponse createActiveTokenResponse(String token, Key tokenKey) {
        return new TokenResponse(token, tokenKey.toString(), null);
    }

    public static TokenResponse createWaitingTokenResponse(String token, Key tokenKey, Long waitingNumber) {
        return new TokenResponse(token, tokenKey.toString(), waitingNumber);
    }
}
