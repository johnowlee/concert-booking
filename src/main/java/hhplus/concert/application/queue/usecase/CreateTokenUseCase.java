package hhplus.concert.application.queue.usecase;

import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.domain.service.QueueCommandService;
import hhplus.concert.core.queue.domain.service.QueueQueryService;
import hhplus.concert.core.queue.domain.service.support.factory.score.ScoreFactory;
import hhplus.concert.core.queue.domain.service.support.factory.token.TokenFactory;
import hhplus.concert.core.queue.domain.service.support.monitor.QueueMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateTokenUseCase {

    private final QueueQueryService queueQueryService;
    private final QueueCommandService queueCommandService;
    private final QueueMonitor queueMonitor;
    private final TokenFactory tokenFactory;
    private final ScoreFactory scoreFactory;

    public Queue execute() {
        boolean isAccessible = queueQueryService.getActiveUserCount() < queueMonitor.getMaxActiveUserCount();
        return isAccessible ? createActiveQueue() : createWaitingQueue();
    }

    private Queue createActiveQueue() {
        String token = tokenFactory.generateToken();
        Queue queue = Queue.createActiveQueue(token);
        queueCommandService.addActiveToken(queue);
        queueCommandService.setActiveTokenTtl(queue, queueMonitor.getTtl());
        return queue;
    }

    private Queue createWaitingQueue() {
        String token = tokenFactory.generateToken();
        long score = scoreFactory.getTimeScore();
        Queue queue = Queue.createWaitingQueue(token, score);
        queueCommandService.addWaitingToken(queue);
        int waitingNumber = queueQueryService.getWaitingNumber(token);
        return Queue.createWaitingQueue(queue.getToken(), waitingNumber);
    }
}
