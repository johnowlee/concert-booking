package hhplus.concert.domain.queue.service;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.exception.code.TokenErrorCode.WAITING_TOKEN;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
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
        given(queueReader.isActiveToken(token)).willReturn(true);

        // when
        Queue result = queueService.getQueueByToken(token);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKeyName()).isEqualTo("ACTIVE");
    }

    @DisplayName("대기 유저의 토큰이면 WAITING 토큰을 반환한다.")
    @Test
    void getQueueByTokenWhenWaitingToken() {
        // given
        String token = "abc";
        int waitingNumber = 10;
        given(queueReader.getWaitingNumber(token)).willReturn(waitingNumber);
        given(queueReader.isActiveToken(token)).willReturn(false);
        given(queueReader.isWaitingToken(token)).willReturn(true);

        // when
        Queue result = queueService.getQueueByToken(token);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKeyName()).isEqualTo("WAITING");
        assertThat(result.getWaitingNumber()).isEqualTo(10);
        verify(queueReader, times(1)).getWaitingNumber("abc");

    }

    @DisplayName("토큰이 유효하지 않으면 예외가 발생한다.")
    @Test
    void getQueueByTokenWithInvalidToken() {
        // given
        String token = "abc";
        given(queueReader.isActiveToken(token)).willReturn(false);
        given(queueReader.isWaitingToken(token)).willReturn(false);

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
        given(queueReader.getTokenCountFromSet(Key.ACTIVE.getKeyName())).willReturn(49L);
        given(queueMonitor.getMaxActiveUserCount()).willReturn(50);

        // when
        Queue result = queueService.createNewQueue(token, score);

        // then
        assertThat(49L).isLessThan(queueMonitor.getMaxActiveUserCount());
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKeyName()).isEqualTo("ACTIVE");
        then(queueWriter).should(times(1)).addActiveToken(any(Queue.class));
        then(queueWriter).should(times(1)).createActiveKey(any(Queue.class), eq(queueMonitor));
    }

    @DisplayName("현재 활성 토큰의 갯수가 활성 최대 수용치 보다 크거나 같으면 대기 토큰이 발급된다.")
    @Test
    void createNewQueueWhenNotAccessible() {
        // given
        String token = "abc";
        long score = 123456L;
        given(queueReader.getTokenCountFromSet(Key.ACTIVE.getKeyName())).willReturn(50L);
        given(queueMonitor.getMaxActiveUserCount()).willReturn(50);

        // when
        Queue result = queueService.createNewQueue(token, score);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKeyName()).isEqualTo("WAITING");
        then(queueWriter).should(times(1)).addWaitingToken(any(Queue.class));
    }

    @DisplayName("활성 토큰이면 예외가 발생하지 않는다.")
    @Test
    void validateToken() {
        // given
        String token = "abc";
        given(queueReader.isWaitingToken(token)).willReturn(false);
        given(queueReader.isNotActiveToken(token)).willReturn(false);

        // when
        queueService.validateToken(token);

        // then
        then(queueReader).should(times(1)).isWaitingToken(token);
        then(queueReader).should(times(1)).isNotActiveToken(token);
    }

    @DisplayName("대기 토큰이면 예외 발생한다.")
    @Test
    void validateTokenWithWaitingToken() {
        // given
        String token = "abc";
        given(queueReader.isWaitingToken(token)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> queueService.validateToken(token))
                        .isInstanceOf(RestApiException.class)
                        .hasMessage(WAITING_TOKEN.getMessage());
        then(queueReader).should(times(1)).isWaitingToken(token);
    }

    @DisplayName("유효하지 않은 토큰이면 예외 발생한다.")
    @Test
    void validateTokenWithNotFoundToken() {
        // given
        String token = "abc";
        given(queueReader.isWaitingToken(token)).willReturn(false);
        given(queueReader.isNotActiveToken(token)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> queueService.validateToken(token))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_TOKEN.getMessage());
        then(queueReader).should(times(1)).isWaitingToken(token);
        then(queueReader).should(times(1)).isNotActiveToken(token);
    }
}