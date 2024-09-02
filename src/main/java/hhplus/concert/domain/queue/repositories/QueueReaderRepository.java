package hhplus.concert.domain.queue.repositories;

import java.util.Set;

public interface QueueReaderRepository {

    Long getTokenSizeFromSet(String activeTokenKey);
    Boolean doseTokenBelongToSet(String activeTokenKey, String token);
    Set<String> getTokensFromSortedSetByRange(String waitingTokenKey, long start, long end);
    Long getTokenRankFromSortedSet(String waitingTokenKey, String token);
    Double getTokenScoreFromSortedSet(String waitingTokenKey, String token);
}
