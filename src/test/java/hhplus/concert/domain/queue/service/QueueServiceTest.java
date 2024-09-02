package hhplus.concert.domain.queue.service;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.service.QueueService;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
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

    @DisplayName("활성 유저로 입장이 가능하면 활성 토큰이 발급된다.")
    @Test
    void createNewQueueWhenAccessible() {
        // given
        String token = "abc";
        long score = 123456L;
        given(queueReader.isAccessible(queueMonitor)).willReturn(true);

        // when
        Queue result = queueService.createNewQueue(token, score);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKeyName()).isEqualTo("ACTIVE");
        verify(queueWriter, times(1)).addActiveToken(any(Queue.class));
        verify(queueWriter, times(1)).createActiveKey(any(Queue.class), eq(queueMonitor));
    }

    @DisplayName("활성 유저로 입장이 불가능하면 대기 토큰이 발급된다.")
    @Test
    void createNewQueueWhenNotAccessible() {
        // given
        String token = "abc";
        long score = 123456L;
        given(queueReader.isAccessible(queueMonitor)).willReturn(false);

        // when
        Queue result = queueService.createNewQueue(token, score);

        // then
        assertThat(result.getToken()).isEqualTo("abc");
        assertThat(result.getKeyName()).isEqualTo("WAITING");
        verify(queueWriter, times(1)).addWaitingToken(any(Queue.class));
    }
}