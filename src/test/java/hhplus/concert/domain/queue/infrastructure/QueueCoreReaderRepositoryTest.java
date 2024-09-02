package hhplus.concert.domain.queue.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueueCoreReaderRepositoryTest {

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    SetOperations<String, String> setOperations;

    @Mock
    ZSetOperations<String, String> zSetOperations;

    @InjectMocks
    QueueCoreReaderRepository queueCoreReaderRepository;

    @DisplayName("Active Key Set의 등록된 유저 수를 반환한다.")
    @Test
    void getTokenSizeFromSet() {
        // given
        String activeUserKey = "ACTIVE";
        Long userCount = 10L;

        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.size(activeUserKey)).willReturn(userCount);

        // when
        Long result = queueCoreReaderRepository.getTokenSizeFromSet(activeUserKey);

        // then
        assertThat(result).isEqualTo(10L);
        verify(setOperations, times(1)).size("ACTIVE");
    }

    @DisplayName("Active key Set에 조회하는 토큰이 포함되어 있으면 true를 반환한다.")
    @Test
    void doseTokenBelongToSet() {
        // given
        String activeUserKey = "ACTIVE";
        String token = "abc123";

        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.isMember(activeUserKey, token)).willReturn(true);

        // when
        Boolean result = queueCoreReaderRepository.doseTokenBelongToSet(activeUserKey, token);

        // then
        assertThat(result).isTrue();
        verify(setOperations, times(1)).isMember("ACTIVE", "abc123");
    }

    @DisplayName("Active key Set에 조회하는 토큰이 없으면 false를 반환한다.")
    @Test
    void doseTokenBelongToSet_withNotActiveUserToken() {
        // given
        String activeUserKey = "ACTIVE";
        String token = "abc123";

        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.isMember(activeUserKey, token)).willReturn(false);

        // when
        Boolean result = queueCoreReaderRepository.doseTokenBelongToSet(activeUserKey, token);

        // then
        assertThat(result).isFalse();
        verify(setOperations, times(1)).isMember("ACTIVE", "abc123");
    }

    @DisplayName("Waiting Key SortedSet에 지정된 범위의 대기 유저 목록을 반환한다.")
    @Test
    void getTokensFromSortedSetByRange() {
        // given
        String waitingUserKey = "WAITING";
        long start = 0L;
        long end = 5L;
        Set<String> waitingUsers = Set.of("abc123", "def456", "hij789");

        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);
        given(zSetOperations.range(waitingUserKey, start, end)).willReturn(waitingUsers);

        // when
        Set<String> result = queueCoreReaderRepository.getTokensFromSortedSetByRange(waitingUserKey, start, end);

        // then
        assertThat(result).hasSize(3);
        assertThat(result).isEqualTo(waitingUsers);
        verify(zSetOperations, times(1)).range("WAITING", 0L, 5);
    }

    @DisplayName("Waiting Key SortedSet에서 대기 유저 토큰의 대기순서를 조회한다.")
    @Test
    void getTokenRankFromSortedSet() {
        // given
        String waitingUserKey = "WAITING";
        String token = "abc123";
        Long rank = 1L;

        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);
        given(zSetOperations.rank(waitingUserKey, token)).willReturn(rank);

        // when
        Long result = queueCoreReaderRepository.getTokenRankFromSortedSet(waitingUserKey, token);

        // then
        assertThat(result).isEqualTo(1L);
        verify(zSetOperations, times(1)).rank("WAITING", "abc123");
    }

    @DisplayName("Waiting Key SortedSet에서 존재하지 않는 유저 토큰의 대기 순번은 null을 반환한다.")
    @Test
    void getTokenRankFromSortedSet_withNotExistedToken() {
        // given
        String waitingUserKey = "WAITING";
        String token = "abc123";
        Long rank = null;

        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);
        given(zSetOperations.rank(waitingUserKey, token)).willReturn(rank);

        // when
        Long result = queueCoreReaderRepository.getTokenRankFromSortedSet(waitingUserKey, token);

        // then
        assertThat(result).isNull();
        verify(zSetOperations, times(1)).rank("WAITING", "abc123");
    }

    @DisplayName("Waiting Key SortedSet에서 유저 토큰의 점수 반환한다.")
    @Test
    void getTokenScoreFromSortedSet() {
        // given
        String waitingUserKey = "WAITING";
        String token = "abc123";
        Double score = 1234.56;

        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);
        given(zSetOperations.score(waitingUserKey, token)).willReturn(score);

        // when
        Double result = queueCoreReaderRepository.getTokenScoreFromSortedSet(waitingUserKey, token);

        // then
        assertThat(result).isEqualTo(1234.56);
        verify(zSetOperations).score("WAITING", "abc123");
    }

    @DisplayName("Waiting Key SortedSet에서 존재하지 않는 유저 토큰의 점수는 null을 반환한다.")
    @Test
    void getTokenScoreFromSortedSet_withNotExistedToken() {
        // given
        String waitingUserKey = "WAITING";
        String token = "abc123";
        Double score = null;

        given(redisTemplate.opsForZSet()).willReturn(zSetOperations);
        given(zSetOperations.score(waitingUserKey, token)).willReturn(score);

        // when
        Double result = queueCoreReaderRepository.getTokenScoreFromSortedSet(waitingUserKey, token);

        // then
        assertThat(result).isNull();
        verify(zSetOperations).score("WAITING", "abc123");
    }
}