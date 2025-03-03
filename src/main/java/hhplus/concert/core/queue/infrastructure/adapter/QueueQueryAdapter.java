package hhplus.concert.core.queue.infrastructure.adapter;

import hhplus.concert.core.queue.domain.port.QueueQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class QueueQueryAdapter implements QueueQueryPort {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long findTokenSizeFromSet(String activeTokenKey) {
        return redisTemplate.opsForSet().size(activeTokenKey);
    }

    @Override
    public Boolean doseTokenBelongToSet(String activeTokenKey, String token) {
        return redisTemplate.opsForSet().isMember(activeTokenKey, token);
    }

    @Override
    public Set<String> findTokensFromSortedSetByRange(String waitingTokenKey, long start, long end) {
        return redisTemplate.opsForZSet().range(waitingTokenKey, start, end);
    }

    @Override
    public Long findTokenRankFromSortedSet(String waitingTokenKey, String token) {
        return redisTemplate.opsForZSet().rank(waitingTokenKey, token);
    }

    @Override
    public Double findTokenScoreFromSortedSet(String waitingTokenKey, String token) {
        return redisTemplate.opsForZSet().score(waitingTokenKey, token);
    }
}
