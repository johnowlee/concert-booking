package hhplus.concert.core.queue.infrastructure;

import hhplus.concert.core.queue.domain.model.Key;
import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.infrastructure.adapter.QueueCommandAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueueCoreWriterRepositoryTest {

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    SetOperations<String, String> setOperations;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    QueueCommandAdapter queueCoreWriterRepository;

    @DisplayName("유저 토큰을 Active Key Set에 저장한다.")
    @Test
    void addTokenToSet() {
        // give
        String token = "abc123";
        Queue queue = Queue.createActiveQueue(token);
        given(redisTemplate.opsForSet()).willReturn(setOperations);


        // when
        queueCoreWriterRepository.addTokenToSet(queue);
        String result = queue.getKeyName();

        // then
        String activeKeyName = Key.ACTIVE.getKeyName();
        assertThat(result).isEqualTo(activeKeyName);
        verify(setOperations, times(1)).add(eq(activeKeyName), eq("abc123"));
    }

    @DisplayName("유저 토큰을 Active Key Set에서 삭제한다.")
    @Test
    void removeTokenFromSet() {
        // given
        String token = "abc123";
        Queue queue = Queue.createActiveQueue(token);
        given(redisTemplate.opsForSet()).willReturn(setOperations);

        // when
        queueCoreWriterRepository.removeTokenFromSet(queue);
        String result = queue.getKeyName();

        // then
        String activeKeyName = Key.ACTIVE.getKeyName();
        assertThat(result).isEqualTo(activeKeyName);
        verify(setOperations, times(1)).remove(eq(activeKeyName), eq("abc123"));
    }

    @DisplayName("유저 토큰을 Waiting Key SortedSet에 저장한다.")
    @Test
    void addTokenToSortedSet() {
        // given
        String token = "abc123";
        long score = 1723440620696L;
        Queue queue = Queue.createWaitingQueue(token, score);
        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);

        // when
        queueCoreWriterRepository.addTokenToSortedSet(queue);
        String result = queue.getKeyName();

        // then
        String waitingKeyName = Key.WAITING.getKeyName();
        assertThat(result).isEqualTo(waitingKeyName);
        verify(zSetOperations, times(1)).add(eq(waitingKeyName), eq("abc123"), eq((double) 1723440620696L));
    }

    @DisplayName("유저 토큰을 Waiting Key SortedSet에서 삭제한다.")
    @Test
    void removeTokenFromSortedSet() {
        // given
        String token = "abc123";
        Queue queue = Queue.createWaitingQueue(token);
        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);

        // when
        queueCoreWriterRepository.removeTokenFromSortedSet(queue);
        String result = queue.getKeyName();

        // then
        String waitingKeyName = Key.WAITING.getKeyName();
        assertThat(result).isEqualTo(waitingKeyName);
        verify(zSetOperations, times(1)).remove(eq(waitingKeyName), eq("abc123"));
    }

    @DisplayName("유저 토큰 값으로 유효시간을 갖는 key를 저장한다. ")
    @Test
    void createTtlToken() {
        // given
        String token = "abc123";
        Queue queue = Queue.createActiveQueue(token);
        long timeout = 300L;
        TimeUnit timeUnit = SECONDS;

        given(redisTemplate.opsForValue()).willReturn(valueOperations);

        // when
        queueCoreWriterRepository.createTtlToken(queue, timeout, timeUnit);
        String result = queue.getKeyName();

        // then
        String activeUserKey = Key.ACTIVE.getKeyName();
        assertThat(result).isEqualTo(activeUserKey);
        verify(valueOperations, times(1)).set(eq("abc123"), eq(activeUserKey), eq(300L), eq(SECONDS));
    }
}