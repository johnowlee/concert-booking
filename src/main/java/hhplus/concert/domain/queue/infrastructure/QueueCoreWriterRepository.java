package hhplus.concert.domain.queue.infrastructure;

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
    public void addUserToActiveSet(Queue queue) {
        redisTemplate.opsForSet().add(queue.getKeyName(), queue.getToken());
    }

    @Override
    public void removeUserFromActiveSet(Queue queue) {
        redisTemplate.opsForSet().remove(queue.getKeyName(), queue.getToken());
    }

    @Override
    public void addUserToWaitingSortedSet(Queue queue) {
        redisTemplate.opsForZSet().add(queue.getKeyName(), queue.getToken(), queue.getScore());
    }

    @Override
    public void removeUserFromWaitingSortedSet(Queue queue) {
        redisTemplate.opsForZSet().remove(queue.getKeyName(), queue.getToken());
    }

    @Override
    public void createUserTimeout(Queue queue, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(queue.getToken(), queue.getKeyName(), timeout, timeUnit);
    }
}
