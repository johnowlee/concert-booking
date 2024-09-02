package hhplus.concert.domain.queue.service;

import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisKeyEventService {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;
    private final QueueMonitor queueMonitor;

    public void expire(String token) {
        removeTokenFromActive(token);
        moveFirstWaiterToActive();
    }

    private void removeTokenFromActive(String token) {
        queueWriter.removeActiveToken(Queue.createActiveQueue(token));
    }

    private void moveFirstWaiterToActive() {
        getFirstTokenFromWaiting().ifPresent(this::activateFirstWaiter);
    }

    private Optional<String> getFirstTokenFromWaiting() {
        Set<String> tokens = queueReader.getTokensFromSortedSetByRange(Key.WAITING, 0, 0);
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
        queueWriter.removeWaitingToken(Queue.createWaitingQueue(token));
    }

    private void addTokenToActive(String token) {
        queueWriter.addActiveToken(Queue.createActiveQueue(token));
    }

    private void setTokenAsActiveUser(String token) {
        queueWriter.createActiveKey(Queue.createActiveQueue(token), queueMonitor);
    }
}
