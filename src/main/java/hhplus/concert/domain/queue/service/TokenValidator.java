package hhplus.concert.domain.queue.service;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;

@Service
@RequiredArgsConstructor
public class TokenValidator {

    private final QueueReader queueReader;

    public boolean isValid(String token) {
        if(queueReader.isActiveToken(token)) return true;
        if(queueReader.isWaitingToken(token)) return false;
        throw new RestApiException(NOT_FOUND_TOKEN);
    }
}
