package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
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

import static java.util.concurrent.TimeUnit.*;
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
    QueueCoreWriterRepository queueCoreWriterRepository;

    @DisplayName("유저 토큰을 Active Key Set에 저장한다.")
    @Test
    void addUserToActiveSet() {
        // give
        String token = "abc123";
        Queue queue = Queue.createActiveQueue(token);
        given(redisTemplate.opsForSet()).willReturn(setOperations);


        // when
        queueCoreWriterRepository.addUserToActiveSet(queue);

        // then
        String keyName = queue.getKeyName();
        assertThat(keyName).isEqualTo("ACTIVE");
        verify(setOperations, times(1)).add(eq("ACTIVE"), eq("abc123"));
    }

    @DisplayName("유저 토큰을 Active Key Set에서 삭제한다.")
    @Test
    void removeUserFromActiveSet() {
        // given
        String token = "abc123";
        Queue queue = Queue.createActiveQueue(token);
        given(redisTemplate.opsForSet()).willReturn(setOperations);

        // when
        queueCoreWriterRepository.removeUserFromActiveSet(queue);

        // then
        String keyName = queue.getKeyName();
        assertThat(keyName).isEqualTo("ACTIVE");
        verify(setOperations, times(1)).remove(eq("ACTIVE"), eq("abc123"));
    }

    @DisplayName("유저 토큰을 Waiting Key SortedSet에 저장한다.")
    @Test
    void addUserToWaitingSortedSet() {
        // given
        String token = "abc123";
        long score = 1723440620696L;
        Queue queue = Queue.createWaitingQueue(token, score);
        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);

        // when
        queueCoreWriterRepository.addUserToWaitingSortedSet(queue);

        // then
        String keyName = queue.getKeyName();
        assertThat(keyName).isEqualTo("WAITING");
        verify(zSetOperations, times(1)).add(eq("WAITING"), eq("abc123"), eq((double) 1723440620696L));
    }

    @DisplayName("유저 토큰을 Waiting Key SortedSet에서 삭제한다.")
    @Test
    void removeUserFromWaitingSortedSet() {
        // given
        String token = "abc123";
        Queue queue = Queue.createWaitingQueue(token);
        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);

        // when
        queueCoreWriterRepository.removeUserFromWaitingSortedSet(queue);

        // then
        String keyName = queue.getKeyName();
        assertThat(keyName).isEqualTo("WAITING");
        verify(zSetOperations, times(1)).remove(eq("WAITING"), eq("abc123"));
    }

    @DisplayName("유저 토큰 값으로 유효시간을 갖는 key를 저장한다. ")
    @Test
    void createUserTimeout() {
        // given
        String token = "abc123";
        Queue queue = Queue.createActiveQueue(token);
        long timeout = 300L;
        TimeUnit timeUnit = SECONDS;

        given(redisTemplate.opsForValue()).willReturn(valueOperations);

        // when
        queueCoreWriterRepository.createUserTimeout(queue, timeout, timeUnit);

        // then
        String keyName = queue.getKeyName();
        assertThat(keyName).isEqualTo("ACTIVE");
        verify(valueOperations, times(1)).set(eq("abc123"), eq("ACTIVE"), eq(300L), eq(SECONDS));
    }
}