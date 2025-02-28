package hhplus.concert.domain.queue.service;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hhplus.concert.representer.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.representer.exception.code.TokenErrorCode.WAITING_TOKEN;
import static hhplus.concert.domain.queue.model.Key.ACTIVE;
import static hhplus.concert.domain.queue.model.Key.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class QueueServiceTest extends IntegrationTestSupport {

    @MockBean
    QueueReader queueReader;

    @MockBean
    QueueWriter queueWriter;

    @MockBean
    QueueMonitor queueMonitor;

    @Autowired
    QueueService queueService;

    @DisplayName("활성 유저의 토큰이면 ACTIVE 토큰을 반환한다.")
    @Test
    void getQueueByTokenWhenActiveToken() {
        // given
        String token = "abc";
        given(queueReader.doseTokenBelongToSet(ACTIVE, token)).willReturn(true);

        // when
        Queue result = queueService.getQueueByToken(token);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKey()).isEqualTo(ACTIVE);
    }

    @DisplayName("대기 유저의 토큰이면 WAITING 토큰을 반환한다.")
    @Test
    void getQueueByTokenWhenWaitingToken() {
        // given
        String token = "abc";
        Long rank = 10L;
        given(queueReader.getTokenRankFromSortedSet(WAITING, token)).willReturn(rank);
        given(queueReader.doseTokenBelongToSet(ACTIVE, token)).willReturn(false);
        given(queueReader.getTokenScoreFromSortedSet(WAITING, token)).willReturn(1.0);

        // when
        Queue result = queueService.getQueueByToken(token);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKey()).isEqualTo(WAITING);
        assertThat(result.getWaitingNumber()).isEqualTo(11);
        verify(queueReader, times(1)).getTokenRankFromSortedSet(WAITING, "abc");

    }

    @DisplayName("토큰이 유효하지 않으면 예외가 발생한다.")
    @Test
    void getQueueByTokenWithInvalidToken() {
        // given
        String token = "abc";
        given(queueReader.doseTokenBelongToSet(ACTIVE, token)).willReturn(false);
        given(queueReader.getTokenScoreFromSortedSet(WAITING, token)).willReturn(null);

        // when & then
        assertThatThrownBy(() -> queueService.getQueueByToken("abc"))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_TOKEN.getMessage());
    }

    @DisplayName("현재 활성 토큰의 갯수가 활성 최대 수용치 보다 적으면 활성 토큰이 발급된다.")
    @Test
    void createNewQueueWhenAccessible() {
        // given
        String token = "abc";
        long score = 123456L;
        given(queueReader.getTokenCountFromSet(ACTIVE)).willReturn(49L);
        given(queueMonitor.getMaxActiveUserCount()).willReturn(50);

        // when
        Queue result = queueService.createNewQueue(token, score);

        // then
        assertThat(49L).isLessThan(queueMonitor.getMaxActiveUserCount());
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKey()).isEqualTo(ACTIVE);
        then(queueWriter).should(times(1)).addActiveToken(any(Queue.class));
        then(queueWriter).should(times(1)).createActiveKey(any(Queue.class), eq(queueMonitor));
    }

    @DisplayName("현재 활성 토큰의 갯수가 활성 최대 수용치 보다 크거나 같으면 대기 토큰이 발급된다.")
    @Test
    void createNewQueueWhenNotAccessible() {
        // given
        String token = "abc";
        long score = 123456L;
        given(queueReader.getTokenCountFromSet(Key.ACTIVE)).willReturn(50L);
        given(queueMonitor.getMaxActiveUserCount()).willReturn(50);

        // when
        Queue result = queueService.createNewQueue(token, score);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKey()).isEqualTo(WAITING);
        then(queueWriter).should(times(1)).addWaitingToken(any(Queue.class));
    }

    @DisplayName("활성 토큰이면 예외가 발생하지 않는다.")
    @Test
    void validateToken() {
        // given
        String token = "abc";
        Key key = ACTIVE;
        given(queueReader.getTokenScoreFromSortedSet(WAITING, token)).willReturn(null);
        given(queueReader.doseTokenBelongToSet(key, token)).willReturn(true);

        // when
        queueService.validateToken(token);

        // then
        then(queueReader).should(times(1)).getTokenScoreFromSortedSet(WAITING, token);
        then(queueReader).should(times(1)).doseTokenBelongToSet(key, token);
    }

    @DisplayName("대기 토큰이면 예외 발생한다.")
    @Test
    void validateTokenWithWaitingToken() {
        // given
        String token = "abc";
        given(queueReader.getTokenScoreFromSortedSet(WAITING, token)).willReturn(1.0);

        // when & then
        assertThatThrownBy(() -> queueService.validateToken(token))
                        .isInstanceOf(RestApiException.class)
                        .hasMessage(WAITING_TOKEN.getMessage());
        then(queueReader).should(times(1)).getTokenScoreFromSortedSet(WAITING, token);
    }

    @DisplayName("유효하지 않은 토큰이면 예외 발생한다.")
    @Test
    void validateTokenWithNotFoundToken() {
        // given
        String token = "abc";
        Key key = ACTIVE;
        given(queueReader.getTokenScoreFromSortedSet(WAITING, token)).willReturn(null);
        given(queueReader.doseTokenBelongToSet(key, token)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> queueService.validateToken(token))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_TOKEN.getMessage());
        then(queueReader).should(times(1)).getTokenScoreFromSortedSet(WAITING, token);
        then(queueReader).should(times(1)).doseTokenBelongToSet(key, token);
    }
}