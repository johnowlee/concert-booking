package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.api.queue.usecase.response.QueueResponse;
import hhplus.concert.domain.queue.support.QueueService;
import lombok.RequiredArgsConstructor;

import static hhplus.concert.api.queue.usecase.response.QueueResponse.createQueueResponse;

@RequiredArgsConstructor
@UseCase
public class FindTokenUseCase {

    private final QueueService queueService;

    public QueueResponse execute(QueueTokenRequest request) {
        return createQueueResponse(queueService.getQueueByToken(request.token()));
    }
}
