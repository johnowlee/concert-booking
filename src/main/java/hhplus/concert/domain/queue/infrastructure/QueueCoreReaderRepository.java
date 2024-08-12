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
    public Long getActiveUserCount(String activeUserKey) {
        return redisTemplate.opsForSet().size(activeUserKey);
    }

    @Override
    public Boolean isActiveUser(String activeUserKey, String token) {
        return redisTemplate.opsForSet().isMember(activeUserKey, token);
    }

    @Override
    public Set<String> getWaitingUsersByRange(String waitingUserKey, long start, long end) {
        return redisTemplate.opsForZSet().range(waitingUserKey, start, end);
    }

    @Override
    public Long getWaitingUserRank(String waitingUserKey, String token) {
        return redisTemplate.opsForZSet().rank(waitingUserKey, token);
    }

    @Override
    public Double getWaitingUserScore(String waitingUserKey, String token) {
        return redisTemplate.opsForZSet().score(waitingUserKey, token);
    }
}
