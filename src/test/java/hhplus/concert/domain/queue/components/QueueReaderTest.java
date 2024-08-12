package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static hhplus.concert.domain.queue.model.QueuePolicy.MAX_CONCURRENT_USERS;
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

    @DisplayName("Active Set의 사이즈가 50개 미만이라면 true를 반환한다.")
    @Test
    void isAccessible_ReturnTrue_WhenSetSizeLessThan50() {
        // given
        given(queueReaderRepository.getActiveUserCount(Key.ACTIVE.toString())).willReturn(1L);

        // when
        Boolean actual = queueReader.isAccessible();

        // then
        assertThat(actual).isTrue();
        verify(queueReaderRepository, times(1)).getActiveUserCount(eq(Key.ACTIVE.toString()));
    }

    @DisplayName("Active Set이 null이면 true를 반환한다.")
    @Test
    void isAccessible_ReturnTrue_WhenSetIsNull() {
        // given
        given(queueReaderRepository.getActiveUserCount(Key.ACTIVE.toString())).willReturn(null);

        // when
        Boolean actual = queueReader.isAccessible();

        // then
        assertThat(actual).isTrue();
        verify(queueReaderRepository, times(1)).getActiveUserCount(eq(Key.ACTIVE.toString()));
    }

    @DisplayName("Active Set이 MAX_CONCURRENT_USERS 사이즈이면 false를 반환한다.")
    @Test
    void isAccessible_ReturnFalse_WhenSetSizeIsMax() {
        // given
        given(queueReaderRepository.getActiveUserCount(Key.ACTIVE.toString())).willReturn(MAX_CONCURRENT_USERS.getValue());

        // when
        Boolean actual = queueReader.isAccessible();

        // then
        assertThat(actual).isFalse();
        verify(queueReaderRepository, times(1)).getActiveUserCount(eq(Key.ACTIVE.toString()));
    }

    @DisplayName("토큰이 Active Set에 포함되어 있으면 true를 반환 한다.")
    @Test
    void isActiveToken_ReturnTrue_WhenTokenIsInActiveSet() {
        // given
        String token = "abc123";
        given(queueReaderRepository.isActiveUser(Key.ACTIVE.toString(), token)).willReturn(true);

        // when
        Boolean actual = queueReader.isActiveToken(token);

        // then
        assertThat(actual).isTrue();
        verify(queueReaderRepository, times(1)).isActiveUser(eq(Key.ACTIVE.toString()), eq(token));
    }

    @DisplayName("토큰이 Active Set에 포함되어 있지 않으면 false를 반환 한다.")
    @Test
    void isActiveToken_ReturnFalse_WhenTokenIsNotInActiveSet() {
        // given
        String token = "abc123";
        given(queueReaderRepository.isActiveUser(Key.ACTIVE.toString(), token)).willReturn(false);

        // when
        Boolean actual = queueReader.isActiveToken(token);

        // then
        assertThat(actual).isFalse();
        verify(queueReaderRepository, times(1)).isActiveUser(eq(Key.ACTIVE.toString()), eq(token));
    }

    @DisplayName("토큰이 Waiting Sroted Set에 포함되어 있으면 true를 반환 한다.")
    @Test
    void isWaitingToken_ReturnTrue_WhenTokenInWaitingSortedSet() {
        // given
        Double score = 1.0;
        String token = "abc123";
        given(queueReaderRepository.getWaitingUserScore(Key.WAITING.toString(), token)).willReturn(score);

        // when
        Boolean actual = queueReader.isWaitingToken(token);

        // then
        assertThat(actual).isTrue();
        verify(queueReaderRepository, times(1)).getWaitingUserScore(eq(Key.WAITING.toString()), eq(token));
    }

    @DisplayName("토큰이 Waiting Sroted Set에 포함되어 있지 않으면 false를 반환 한다.")
    @Test
    void isWaitingToken_ReturnFalse_WhenTokenNotInWaitingSortedSet() {
        // given
        String token = "abc123";
        given(queueReaderRepository.getWaitingUserScore(Key.WAITING.toString(), token)).willReturn(null);

        // when
        Boolean actual = queueReader.isWaitingToken(token);

        // then
        assertThat(actual).isFalse();
        verify(queueReaderRepository, times(1)).getWaitingUserScore(eq(Key.WAITING.toString()), eq(token));
    }

    @DisplayName("Waiting Sorted Set에 조회하는 토큰이 있으면 해당 토큰의 Rank + 1 값을 반환한다.")
    @Test
    void getWaitingNumber_ReturnRank() {
        // given
        String token = "abc123";
        long rank = 0L;
        given(queueReaderRepository.getWaitingUserRank(Key.WAITING.toString(), token)).willReturn(rank);

        // when
        int actual = queueReader.getWaitingNumber(token);

        // then
        assertThat(actual).isEqualTo(rank + 1);
        verify(queueReaderRepository, times(1)).getWaitingUserRank(eq(Key.WAITING.toString()), eq(token));
    }

    @DisplayName("Waiting Sorted Set에 조회하는 토큰이 있으면 해당 토큰의 Rank + 1 값을 반환한다.")
    @Test
    void getWaitingNumber_ReturNull_WhenTokenIsNotInWaitingSortedSet() {
        // given
        String token = "abc123";
        given(queueReaderRepository.getWaitingUserRank(Key.WAITING.toString(), token)).willReturn(null);

        // when
        int actual = queueReader.getWaitingNumber(token);

        // then
        assertThat(actual).isNull();
        verify(queueReaderRepository, times(1)).getWaitingUserRank(eq(Key.WAITING.toString()), eq(token));
    }

    @DisplayName("Waiting Sorted Set의 첫번째 토큰을 반환한다.")
    @Test
    void getFirstWaiter_ReturnStringTypeSet() {
        // given
        Set<String> expected = new HashSet<>();
        String token = "abc123";
        expected.add(token);
        given(queueReaderRepository.getWaitingUsersByRange(Key.WAITING.toString(), 0, 0)).willReturn(expected);

        // when
        Set<String> actual = queueReader.getFirstWaiter();

        // then
        assertThat(actual).isEqualTo(expected);
        assertThat(actual.contains(token)).isTrue();
        verify(queueReaderRepository, times(1)).getWaitingUsersByRange(eq(Key.WAITING.toString()), eq(0L), eq(0L));
    }
}