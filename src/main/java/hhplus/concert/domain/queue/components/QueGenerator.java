package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.concert.domain.queue.model.QueueStatus.PROCESSING;
import static hhplus.concert.domain.queue.model.QueueStatus.WAITING;

@Component
@RequiredArgsConstructor
public class QueGenerator {

    private final QueueValidator queueValidator;
    private final QueueReader queueReader;
    private final QueueWriter queueWriter;

    public Queue getQueue(User user) {
        /**
         * 대기열 대기여부 확인 후 대기상태,포지션 세팅
         * queue 새로 생성
         */
        long position = 0;
        QueueStatus status = PROCESSING;

        boolean isProcessable = queueValidator.isQueueProcessable(queueReader.getProcessingQueues());
        if (!isProcessable) {
            position = queueReader.getLastWaitingPosition()+1;
            status = WAITING;
        }

        return queueWriter.saveQueue(Queue.createQueue(user, position, status));
    }
}
