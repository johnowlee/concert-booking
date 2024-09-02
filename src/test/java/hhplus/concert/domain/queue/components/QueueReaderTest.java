package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static hhplus.concert.domain.queue.model.Key.ACTIVE;
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

    @DisplayName("토큰이 대기열에 포함되어 있으면 true를 반환 한다.")
    @Test
    void isWaitingToken() {
        // given
        Double score = 1.0;
        String token = "abc123";
        String waitingUserKey = "WAITING";
        given(queueReaderRepository.getTokenScoreFromSortedSet(waitingUserKey, token)).willReturn(score);

        // when
        Boolean result = queueReader.isWaitingToken(token);

        // then
        assertThat(result).isTrue();
        verify(queueReaderRepository, times(1)).getTokenScoreFromSortedSet(eq("WAITING"), eq("abc123"));
    }

    @DisplayName("토큰이 대기열에 포함되어 있지 않으면 false를 반환 한다.")
    @Test
    void isWaitingTokenIfNotExists() {
        // given
        Double score = null;
        String token = "abc123";
        String waitingUserKey = "WAITING";
        given(queueReaderRepository.getTokenScoreFromSortedSet(waitingUserKey, token)).willReturn(score);

        // when
        Boolean result = queueReader.isWaitingToken(token);

        // then
        assertThat(result).isFalse();
        verify(queueReaderRepository, times(1)).getTokenScoreFromSortedSet(eq("WAITING"), eq("abc123"));
    }

    @DisplayName("대기열에서 토큰의 대기순번을 조회한다.")
    @Test
    void getWaitingNumber() {
        // given
        String token = "abc123";
        Long rank = 5L;
        String waitingUserKey = "WAITING";
        given(queueReaderRepository.getTokenRankFromSortedSet(waitingUserKey, token)).willReturn(rank);

        // when
        int waitingNumber = queueReader.getWaitingNumber(token);

        // then
        assertThat(waitingNumber).isEqualTo(5 + 1);
        verify(queueReaderRepository).getTokenRankFromSortedSet("WAITING", "abc123");
    }

    @DisplayName("대기열에 토큰이 없을 경우 0을 반환한다.")
    @Test
    void getWaitingNumberIfNotExists() {
        // given
        String token = "abc123";
        String waitingUserKey = "WAITING";
        given(queueReaderRepository.getTokenRankFromSortedSet(waitingUserKey, token)).willReturn(null);

        // when
        int result = queueReader.getWaitingNumber(token);

        // then
        assertThat(result).isZero();
        verify(queueReaderRepository, times(1)).getTokenRankFromSortedSet(eq("WAITING"), eq("abc123"));
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
}