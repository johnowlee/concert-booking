package hhplus.concert.api.queue.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import hhplus.concert.domain.queue.model.Key;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenResponse(String token, String key, Long waitingNumber) {

    public static TokenResponse createActiveTokenResponse(String token) {
        return new TokenResponse(token, Key.ACTIVE.toString(), null);
    }

    public static TokenResponse createWaitingTokenResponse(String token, Long waitingNumber) {
        return new TokenResponse(token, Key.WAITING.toString(), waitingNumber);
    }
}
