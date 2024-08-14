package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.api.queue.usecase.response.QueueResponse;
import hhplus.concert.domain.queue.support.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.queue.usecase.response.QueueResponse.createQueueResponse;

@Service
@RequiredArgsConstructor
public class FindTokenUseCase {

    private final QueueService queueService;

    public QueueResponse execute(QueueTokenRequest request) {
        return createQueueResponse(queueService.getQueueByToken(request.token()));
    }
}
