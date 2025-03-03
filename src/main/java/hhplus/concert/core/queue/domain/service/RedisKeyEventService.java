package hhplus.concert.core.queue.domain.service;

import hhplus.concert.core.queue.domain.model.Key;
import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.domain.service.support.monitor.QueueMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisKeyEventService {

    private final QueueQueryService queueQueryService;
    private final QueueCommandService queueCommandService;
    private final QueueMonitor queueMonitor;

    public void expire(String token) {
        removeTokenFromActive(token);
        moveFirstWaiterToActive();
    }

    private void removeTokenFromActive(String token) {
        queueCommandService.removeActiveToken(Queue.createActiveQueue(token));
    }

    private void moveFirstWaiterToActive() {
        getFirstTokenFromWaiting().ifPresent(this::activateFirstWaiter);
    }

    private Optional<String> getFirstTokenFromWaiting() {
        Set<String> tokens = queueQueryService.findTokensFromSortedSetByRange(Key.WAITING, 0, 0);
        return Optional.ofNullable(tokens)
                .filter((tokenSet) -> !tokenSet.isEmpty())
                .map(tokenSet -> tokenSet.iterator().next());
    }

    private void activateFirstWaiter(String token) {
        removeTokenFromWaiting(token);
        addTokenToActive(token);
        setTokenAsActiveUser(token);
    }

    private void removeTokenFromWaiting(String token) {
        queueCommandService.removeWaitingToken(Queue.createWaitingQueue(token));
    }

    private void addTokenToActive(String token) {
        queueCommandService.addActiveToken(Queue.createActiveQueue(token));
    }

    private void setTokenAsActiveUser(String token) {
        queueCommandService.setActiveTokenTtl(Queue.createActiveQueue(token), queueMonitor.getTtl());
    }
}
