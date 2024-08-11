package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.support.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.queue.dto.response.QueueResponse.createQueueResponse;

@Service
@RequiredArgsConstructor
public class CreateTokenUseCase {

    private final QueueService queueService;

    public QueueResponse execute() {
        return createQueueResponse(queueService.createNewQueue());
    }
}
