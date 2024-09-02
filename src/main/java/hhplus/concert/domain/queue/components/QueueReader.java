package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static hhplus.concert.domain.queue.model.Key.WAITING;

@Component
@RequiredArgsConstructor
public class QueueReader {

    private final QueueReaderRepository queueReaderRepository;

    public Long getTokenCountFromSet(Key key) {
        return queueReaderRepository.getTokenSizeFromSet(key.getKeyName());
    }

    public boolean doseTokenBelongToSet(Key key, String token) {
        return queueReaderRepository.doseTokenBelongToSet(key.getKeyName(), token);
    }

    public Double getTokenScoreFromSortedSet(Key key, String token) {
        return queueReaderRepository.getTokenScoreFromSortedSet(key.getKeyName(), token);
    }

    public int getWaitingNumber(String token) {
        Long rank = queueReaderRepository.getTokenRankFromSortedSet(WAITING.getKeyName(), token);
        return calculateWaitingNumberBy(rank);
    }

    public Long getTokenRankFromSortedSet(Key key, String token) {
        return queueReaderRepository.getTokenRankFromSortedSet(key.getKeyName(), token);
    }

    public Set<String> getFirstWaiter() {
        return queueReaderRepository.getTokensFromSortedSetByRange(WAITING.getKeyName(), 0, 0);
    }

    private static int calculateWaitingNumberBy(Long rank) {
        return rank != null ? (int) (rank + 1) : 0;
    }
}
