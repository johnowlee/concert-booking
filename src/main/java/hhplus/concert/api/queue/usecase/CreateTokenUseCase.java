package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static hhplus.concert.api.queue.dto.response.QueueResponse.*;

@Service
@RequiredArgsConstructor
public class CreateTokenUseCase {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;

    public QueueResponse execute() {
        String token = UUID.randomUUID().toString();
        if (queueReader.isAccessible()) {
            return activeQueue(token);
        } else {
            return waitingQueue(token);
        }
    }

    private QueueResponse activeQueue(String token) {
        Queue queue = Queue.createActiveQueue(token);
        queueWriter.addActiveToken(queue);
        queueWriter.createActiveKey(queue);
        return createActiveQueueResponse(queue.getToken());
    }

    private QueueResponse waitingQueue(String token) {
        Queue queue = Queue.createWaitingQueue(token);
        queueWriter.addWaitingToken(queue);
        return createWaitingQueueResponse(queue.getToken(), queueReader.getWaitingNumber(queue.getToken()));
    }
}
