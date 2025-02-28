package hhplus.concert.representer.api.queue.usecase;

import hhplus.concert.representer.api.common.UseCase;
import hhplus.concert.representer.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.representer.api.queue.usecase.response.QueueResponse;
import hhplus.concert.domain.queue.service.QueueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class FindTokenUseCase {

    private final QueueService queueService;

    public QueueResponse execute(QueueTokenRequest request) {
        return QueueResponse.createQueueResponse(queueService.getQueueByToken(request.token()));
    }
}
