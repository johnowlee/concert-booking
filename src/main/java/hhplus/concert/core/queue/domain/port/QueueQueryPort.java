package hhplus.concert.core.queue.domain.port;

import java.util.Set;

public interface QueueQueryPort {

    Long findTokenSizeFromSet(String activeTokenKey);
    Boolean doseTokenBelongToSet(String activeTokenKey, String token);
    Set<String> findTokensFromSortedSetByRange(String waitingTokenKey, long start, long end);
    Long findTokenRankFromSortedSet(String waitingTokenKey, String token);
    Double findTokenScoreFromSortedSet(String waitingTokenKey, String token);
}
