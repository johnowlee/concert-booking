package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static hhplus.concert.domain.queue.model.Key.ACTIVE;
import static hhplus.concert.domain.queue.model.Key.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueueReaderTest {

    @InjectMocks
    QueueReader queueReader;

    @Mock
    QueueReaderRepository queueReaderRepository;

    @Mock
    QueueMonitor queueMonitor;

    @DisplayName("활성 유저 key에 등록된 토큰 수를 반환한다.")
    @Test
    void getTokenCountFromSet() {
        // given
        Long activeUserCount = 30L;
        Key key = ACTIVE;
        given(queueReaderRepository.getTokenSizeFromSet(key.getKeyName())).willReturn(activeUserCount);

        // when
        Long result = queueReader.getTokenCountFromSet(key);

        // then
        assertThat(result).isEqualTo(30L);
    }

    @DisplayName("토큰이 활성 유저에 포함되어 있으면 true를 반환한다.")
    @Test
    void isActiveToken() {
        // given
        String token = "abc123";
        String activeUserKey = "ACTIVE";
        given(queueReaderRepository.doseTokenBelongToSet(activeUserKey, token)).willReturn(true);

        // when
        Boolean result = queueReader.doseTokenBelongToSet(ACTIVE, token);

        // then
        assertThat(result).isTrue();
        verify(queueReaderRepository).doseTokenBelongToSet("ACTIVE", "abc123");
    }

    @DisplayName("토큰이 활성 유저에 포함되어 있지 않으면 false 반환한다.")
    @Test
    void isActiveTokenIfNotExists() {
        // given
        String token = "abc123";
        String activeUserKey = "ACTIVE";
        given(queueReaderRepository.doseTokenBelongToSet(activeUserKey, token)).willReturn(false);

        // when
        Boolean result = queueReader.doseTokenBelongToSet(ACTIVE, token);

        // then
        assertThat(result).isFalse();
        verify(queueReaderRepository, times(1)).doseTokenBelongToSet(eq("ACTIVE"), eq("abc123"));
    }
    
    @DisplayName("Sorted Set 토큰의 스코어를 조회 한다.")
    @Test
    void getTokenScoreFromSortedSet() {
        // given
        Double score = 1.0;
        String token = "abc123";
        Key key = Key.WAITING;
        given(queueReaderRepository.getTokenScoreFromSortedSet(key.getKeyName(), token)).willReturn(score);
        
        // when
        Double result = queueReader.getTokenScoreFromSortedSet(key, token);

        // then
        Assertions.assertThat(result).isEqualTo(1.0);
    }

    @DisplayName("Sorted Set에 토큰이 없으면 스코어는 null을 반환 한다.")
    @Test
    void getTokenScoreFromSortedSetWithNotExistedToken() {
        // given
        Double score = null;
        String token = "abc123";
        Key key = Key.WAITING;
        given(queueReaderRepository.getTokenScoreFromSortedSet(key.getKeyName(), token)).willReturn(score);

        // when
        Double result = queueReader.getTokenScoreFromSortedSet(key, token);

        // then
        Assertions.assertThat(result).isNull();
    }

    @DisplayName("대기 Sorted Set에서 토큰의 rank를 조회한다.")
    @Test
    void getTokenRankFromSortedSet() {
        // given
        String token = "abc123";
        Key key = WAITING;
        given(queueReaderRepository.getTokenRankFromSortedSet(key.getKeyName(), token)).willReturn(1L);

        // when
        Long result = queueReader.getTokenRankFromSortedSet(key, token);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @DisplayName("대기 Sorted Set에서 존재하지 않는 토큰의 rank를 조회 시 null을 반환한다.")
    @Test
    void getTokenRankFromSortedSetWithNotExistedToken() {
        // given
        String token = "abc123";
        Key key = WAITING;
        given(queueReaderRepository.getTokenRankFromSortedSet(key.getKeyName(), token)).willReturn(null);

        // when
        Long result = queueReader.getTokenRankFromSortedSet(key, token);

        // then
        assertThat(result).isNull();
    }

    @DisplayName("대기열의 첫번째 대기자의 토큰을 반환한다.")
    @Test
    void getFirstWaiter() {
        // given
        Set<String> firstWaiter = Set.of("abc123");
        given(queueReaderRepository.getTokensFromSortedSetByRange("WAITING", 0, 0)).willReturn(firstWaiter);

        // when
        Set<String> result = queueReader.getFirstWaiter();

        // then
        assertThat(result).isEqualTo(Set.of("abc123"));
        verify(queueReaderRepository).getTokensFromSortedSetByRange("WAITING", 0, 0);
    }

    @DisplayName("대기 Sorted Set의 토큰들을 범위에 따라 조회한다.")
    @Test
    void getTokensFromSortedSetByRange() {
        // given
        Key key = WAITING;
        long start = 0;
        long end = 1;

        String token1 = "abc";
        String token2 = "def";
        Set<String> tokens = Set.of(token1, token2);

        given(queueReaderRepository.getTokensFromSortedSetByRange(key.getKeyName(), start, end)).willReturn(tokens);

        // when
        Set<String> result = queueReader.getTokensFromSortedSetByRange(key, start, end);

        // then
        Assertions.assertThat(result).hasSize(2)
                .contains(
                        token1, token2
                );
    }
}