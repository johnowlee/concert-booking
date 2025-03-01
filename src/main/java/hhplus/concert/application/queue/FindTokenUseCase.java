package hhplus.concert.application.queue;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.service.QueueService;
import hhplus.concert.representer.api.common.UseCase;
import hhplus.concert.representer.api.queue.request.QueueTokenRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class FindTokenUseCase {

    private final QueueService queueService;

    public Queue execute(QueueTokenRequest request) {
        return queueService.getQueueByToken(request.token());
    }
}
