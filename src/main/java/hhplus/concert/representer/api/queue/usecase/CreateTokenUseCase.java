package hhplus.concert.representer.api.queue.usecase;

import hhplus.concert.representer.api.common.UseCase;
import hhplus.concert.representer.api.queue.usecase.response.QueueResponse;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.service.QueueService;
import hhplus.concert.domain.queue.support.factory.score.ScoreFactory;
import hhplus.concert.domain.queue.support.factory.token.TokenFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class CreateTokenUseCase {

    private final QueueService queueService;
    private final TokenFactory tokenFactory;
    private final ScoreFactory scoreFactory;

    public QueueResponse execute() {
        return QueueResponse.createQueueResponse(createNewQueue());
    }

    private Queue createNewQueue() {
        String token = tokenFactory.generateToken();
        long score = scoreFactory.getTimeScore();
        return queueService.createNewQueue(token, score);
    }
}
