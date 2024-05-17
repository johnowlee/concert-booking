package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.QueuePolicy;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class QueueCoreWriterRepository implements QueueWriterRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void addTokenToSet(Queue queue) {
        redisTemplate.opsForSet().add(queue.getKeyName(), queue.getToken());
    }

    @Override
    public void removeTokenFromSet(Queue queue) {
        redisTemplate.opsForSet().remove(queue.getKeyName(), queue.getToken());
    }

    @Override
    public void addTokenToSortedSet(Queue queue) {
        redisTemplate.opsForZSet().add(queue.getKeyName(), queue.getToken(), queue.getScore());
    }

    @Override
    public void removeTokenFromSortedSet(Queue queue) {
        redisTemplate.opsForZSet().remove(queue.getKeyName(), queue.getToken());
    }

    @Override
    public void createTimeoutKey(Queue queue, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(queue.getToken(), queue.getKeyName(), timeout, timeUnit);
    }
}
