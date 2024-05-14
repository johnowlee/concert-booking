package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.queue.dto.response.TokenResponse;
import hhplus.concert.domain.queue.components.QueueReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.queue.dto.response.TokenResponse.createActiveTokenResponse;
import static hhplus.concert.api.queue.dto.response.TokenResponse.createWaitingTokenResponse;

@Service
@RequiredArgsConstructor
public class FindTokenUseCase {

    private final QueueReader queueReader;

    public TokenResponse execute(String token) {
        if(queueReader.isActiveToken(token)) return createActiveTokenResponse(token);
        if(queueReader.isWaitingToken(token)) return createWaitingTokenResponse(token, queueReader.getWaitingNumber(token));
        throw new RestApiException(NOT_FOUND_TOKEN);
    }
}
