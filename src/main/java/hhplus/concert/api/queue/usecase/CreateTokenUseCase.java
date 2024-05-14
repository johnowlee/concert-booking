package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.TokenResponse;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static hhplus.concert.api.queue.dto.response.TokenResponse.createActiveTokenResponse;
import static hhplus.concert.api.queue.dto.response.TokenResponse.createWaitingTokenResponse;

@Service
@RequiredArgsConstructor
public class CreateTokenUseCase {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;

    public TokenResponse execute() {
        String token = UUID.randomUUID().toString();
        if (queueReader.isAccessible()) {
            Queue queue = Queue.createActiveQueue(token);
            queueWriter.addActiveToken(queue);
            queueWriter.createActiveKey(queue);
            return createActiveTokenResponse(queue.getToken());
        } else {
            Queue queue = Queue.createWaitingQueue(token);
            queueWriter.addWaitingToken(queue);
            return createWaitingTokenResponse(queue.getToken(), queueReader.getWaitingNumber(queue.getToken()));
        }
    }
}
