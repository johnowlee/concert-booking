package hhplus.concert.domain.queue.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.exception.code.TokenErrorCode.WAITING_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenValidator {

    private final QueueReader queueReader;

    public void validateToken(String token) {
        if (queueReader.isWaitingToken(token)) throw new RestApiException(WAITING_TOKEN);
        if (queueReader.isActiveToken(token) == null) throw new RestApiException(NOT_FOUND_TOKEN);
    }
}
