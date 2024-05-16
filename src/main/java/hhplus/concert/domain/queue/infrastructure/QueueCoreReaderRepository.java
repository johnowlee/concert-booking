package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RequiredArgsConstructor
public class QueueCoreReaderRepository implements QueueReaderRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean containsValue(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Set<String> getValuesByRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    @Override
    public Long getRankByValue(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    @Override
    public Double getScoreByValue(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }
}
