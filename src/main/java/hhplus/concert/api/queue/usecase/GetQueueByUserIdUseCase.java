package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.components.QueGenerator;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.user.components.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetQueueByUserIdUseCase {

    private final QueueReader queueReader;
    private final QueGenerator queGenerator;
    private final UserReader userReader;

    @Transactional
    public QueueResponse excute(Long userId) {
        // queue 조회
        Queue queue = queueReader.getProcessingQueueByUserId(userId);

        // queue 검증
        if (queue == null || queue.isExpired()) {
            // queue 생성
            queue = queGenerator.getQueue(userReader.getUserById(userId));
        }
        return QueueResponse.of(queue, queueReader.getRealWaitingPosition(queue.getPosition()));
    }
}
