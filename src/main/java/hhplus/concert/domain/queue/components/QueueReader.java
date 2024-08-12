package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static hhplus.concert.domain.queue.model.QueuePolicy.MAX_CONCURRENT_USERS;

@Component
@RequiredArgsConstructor
public class QueueReader {

    private final QueueReaderRepository queueReaderRepository;

    public Boolean isAccessible() {
        Long concurrentSize = queueReaderRepository.getActiveUserCount(Key.ACTIVE.toString());
        return concurrentSize == null || concurrentSize < MAX_CONCURRENT_USERS.getValue();
    }

    public Boolean isActiveToken(String token) {
        return queueReaderRepository.isActiveUser(Key.ACTIVE.toString(), token);
    }

    public Boolean isWaitingToken(String token) {
        return queueReaderRepository.getWaitingUserScore(Key.WAITING.toString(), token) != null;
    }

    public Long getWaitingNumber(String token) {
        Long rank = queueReaderRepository.getWaitingUserRank(Key.WAITING.toString(), token);
        return rank != null ? rank + 1 : null; // 순번은 1부터 시작하므로 1을 더해줌
    }

    public Set<String> getFirstWaiter() {
        return queueReaderRepository.getWaitingUsersByRange(Key.WAITING.toString(), 0, 0);
    }
}
