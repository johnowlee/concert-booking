package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.service.QueueManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.queue.dto.response.QueueResponse.createQueueResponse;

@Service
@RequiredArgsConstructor
public class FindTokenUseCase {

    private final QueueManager queueManager;

    public QueueResponse execute(String token) {
        return createQueueResponse(queueManager.getQueueByToken(token));
    }
}
