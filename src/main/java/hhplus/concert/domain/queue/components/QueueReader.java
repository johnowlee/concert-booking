package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static hhplus.concert.domain.queue.model.Key.ACTIVE;
import static hhplus.concert.domain.queue.model.Key.WAITING;

@Component
@RequiredArgsConstructor
public class QueueReader {
    // TODO : 리팩토링 -> service or manager 객체 필요.

    private final QueueReaderRepository queueReaderRepository;

    public Boolean isAccessible(QueueMonitor queueMonitor) {
        Long concurrentSize = queueReaderRepository.getActiveUserCount(ACTIVE.getKeyName());
        return concurrentSize == null || concurrentSize < queueMonitor.getMaxActiveUserCount();
    }

    public Boolean isActiveToken(String token) {
        return queueReaderRepository.isActiveUser(ACTIVE.getKeyName(), token);
    }

    public Boolean isWaitingToken(String token) {
        return queueReaderRepository.getWaitingUserScore(WAITING.getKeyName(), token) != null;
    }

    public int getWaitingNumber(String token) {
        Long rank = queueReaderRepository.getWaitingUserRank(WAITING.getKeyName(), token);
        return calculateWaitingNumberBy(rank);
    }

    public Set<String> getFirstWaiter() {
        return queueReaderRepository.getWaitingUsersByRange(WAITING.getKeyName(), 0, 0);
    }

    private static int calculateWaitingNumberBy(Long rank) {
        return rank != null ? (int) (rank + 1) : 0;
    }
}
