package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

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

    public Long getTokenRankFromSortedSet(Key key, String token) {
        return queueReaderRepository.getTokenRankFromSortedSet(key.getKeyName(), token);
    }

    public Set<String> getTokensFromSortedSetByRange(Key key, long start, long end) {
        return queueReaderRepository.getTokensFromSortedSetByRange(key.getKeyName(), start, end);
    }
}
