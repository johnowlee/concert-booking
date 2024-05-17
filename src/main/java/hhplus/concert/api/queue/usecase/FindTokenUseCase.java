package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.components.QueueReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.queue.dto.response.QueueResponse.*;

@Service
@RequiredArgsConstructor
public class FindTokenUseCase {

    private final QueueReader queueReader;

    public QueueResponse execute(String token) {
        if(queueReader.isActiveToken(token)) return createActiveQueueResponse(token);
        if(queueReader.isWaitingToken(token)) return createWaitingQueueResponse(token, queueReader.getWaitingNumber(token));
        throw new RestApiException(NOT_FOUND_TOKEN);
    }
}
