package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.QueueService;
import hhplus.concert.domain.queue.support.factory.score.ScoreFactory;
import hhplus.concert.domain.queue.support.factory.token.TokenFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.queue.dto.response.QueueResponse.createQueueResponse;

@Service
@RequiredArgsConstructor
public class CreateTokenUseCase {

    private final QueueService queueService;
    private final TokenFactory tokenFactory;
    private final ScoreFactory scoreFactory;

    public QueueResponse execute() {
        return createQueueResponse(createNewQueue());
    }

    private Queue createNewQueue() {
        String token = tokenFactory.generateToken();
        long score = scoreFactory.getTimeScore();
        return queueService.createNewQueue(token, score);
    }
}
