package hhplus.concert.application.queue.usecase;

import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.domain.service.QueueQueryService;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import hhplus.concert.representer.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.representer.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FindTokenUseCase {

    private final QueueQueryService queueQueryService;

    public Queue execute(QueueTokenRequest request) {
        String token = request.token();

        if (queueQueryService.isActiveToken(token)) {
            return Queue.createActiveQueue(token);
        }

        if (queueQueryService.isWaitingToken(token)) {
            int waitingNumber = queueQueryService.getWaitingNumber(token);
            return Queue.createWaitingQueue(token, waitingNumber);
        }
        throw new RestApiException(NOT_FOUND_TOKEN);
    }
}
