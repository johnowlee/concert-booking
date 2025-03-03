package hhplus.concert.core.queue.domain.service;

import hhplus.concert.core.queue.domain.model.Key;
import hhplus.concert.core.queue.domain.port.QueueQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class QueueQueryService {

    private final QueueQueryPort queueQueryPort;

    public Long getActiveUserCount() {
        Long count = queueQueryPort.findTokenSizeFromSet(Key.ACTIVE.getKeyName());
        return count == null ? 0 : count;
    }

    public boolean isActiveToken(String token) {
        return queueQueryPort.doseTokenBelongToSet(Key.ACTIVE.getKeyName(), token);
    }

    public boolean isWaitingToken(String token) {
        return queueQueryPort.findTokenScoreFromSortedSet(Key.WAITING.getKeyName(), token) != null;
    }

    public int getWaitingNumber(String token) {
        Long rank = queueQueryPort.findTokenRankFromSortedSet(Key.WAITING.getKeyName(), token);
        return rank == null ? 0 : (int) (rank + 1);
    }

    public Set<String> findTokensFromSortedSetByRange(Key key, long start, long end) {
        return queueQueryPort.findTokensFromSortedSetByRange(key.getKeyName(), start, end);
    }
}
