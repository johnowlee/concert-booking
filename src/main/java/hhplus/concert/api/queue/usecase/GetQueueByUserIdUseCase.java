package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueValidator;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.user.components.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.domain.queue.model.QueueStatus.PROCESSING;
import static hhplus.concert.domain.queue.model.QueueStatus.WAITING;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetQueueByUserIdUseCase {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;
    private final UserReader userReader;
    private final QueueValidator queueValidator;

    @Transactional
    public QueueResponse excute(Long userId) {
        // queue 조회
        Queue queue = queueReader.getProcessingQueueByUserId(userId);

        // queue 검증
        if (!queueValidator.isQueueValid(queue)) {

            /**
             * 대기열 대기여부 확인 후 대기상태,포지션 세팅
             * queue 새로 생성
             */
            boolean isProcessable = queueValidator.isQueueProcessable(queueReader.getProcessingQueues());
            QueueStatus status = isProcessable ? PROCESSING : WAITING;
            long position = isProcessable ? 0 : queueReader.getWaitingPosition();

            queue = queueWriter.saveQueue(Queue.createQueue(userReader.getUserById(userId), position, status));
        }
        return QueueResponse.from(queue);
    }
}
