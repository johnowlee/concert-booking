package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.components.QueueReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetQueueByUserIdUseCase {

    private final QueueReader queueReader;

    public QueueResponse excute(Long userId) {
        return QueueResponse.from(queueReader.getQueueByUserId(userId));
    }
}
