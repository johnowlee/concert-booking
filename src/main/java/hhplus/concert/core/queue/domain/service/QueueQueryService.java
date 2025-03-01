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
        Long count = queueQueryPort.getTokenSizeFromSet(Key.ACTIVE.getKeyName());
        return count == null ? 0 : count;
    }

    public boolean isActiveToken(String token) {
        return queueQueryPort.doseTokenBelongToSet(Key.ACTIVE.getKeyName(), token);
    }

    public boolean isWaitingToken(String token) {
        return queueQueryPort.getTokenScoreFromSortedSet(Key.WAITING.getKeyName(), token) != null;
    }

    public int getWaitingNumber(String token) {
        Long rank = queueQueryPort.getTokenRankFromSortedSet(Key.WAITING.getKeyName(), token);
        return rank == null ? 0 : (int) (rank + 1);
    }

    public Set<String> getTokensFromSortedSetByRange(Key key, long start, long end) {
        return queueQueryPort.getTokensFromSortedSetByRange(key.getKeyName(), start, end);
    }
}
