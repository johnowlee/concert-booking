package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueueStatus;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static hhplus.concert.domain.queue.model.QueueManager.MAX_PROCESSABLE_COUNT;
import static hhplus.concert.domain.queue.model.QueueStatus.PROCESSING;
import static hhplus.concert.domain.queue.model.QueueStatus.WAITING;

@Component
@RequiredArgsConstructor
public class QueueGenerator {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;

    public Queue getQueue(User user) {
        /**
         * 대기열 대기여부 확인 후 대기상태,포지션 세팅
         * queue 새로 생성
         */
        long position = 0;
        QueueStatus status = PROCESSING;

        List<Queue> processingQueues = queueReader.getProcessingQueues();
        if (!isQueueProcessable(processingQueues)) {
            position = queueReader.getLastWaitingPosition()+1;
            status = WAITING;
        }

        return queueWriter.saveQueue(Queue.createQueue(user, position, status));
    }

    private static boolean isQueueProcessable(List<Queue> queues) {
        return queues.stream()
                .filter(q -> !q.isExpired())
                .collect(Collectors.toList()).size() < MAX_PROCESSABLE_COUNT.getValue();
    }
}
