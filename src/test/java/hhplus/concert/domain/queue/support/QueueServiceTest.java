package hhplus.concert.domain.queue.support;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.TokenErrorCode;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {

    @InjectMocks
    private QueueService queueService;

    @Mock
    private QueueReader queueReader;

    @Mock
    private QueueWriter queueWriter;

    @Mock
    QueueMonitor queueMonitor;

    @DisplayName("ACTIVE 상태인 token 조회 시, 해당 토큰을 반환한다.")
    @Test
    void getQueueByToken_ShouldReturnActiveQueue_WhenTokenIsActive() {
        // given
        String token = "active-token";
        given(queueReader.isActiveToken(token)).willReturn(true);

        // when
        Queue queue = queueService.getQueueByToken(token);

        // then
        assertNotNull(queue);
        assertEquals(token, queue.getToken());
        assertEquals(Key.ACTIVE, queue.getKey());
        then(queueReader).should().isActiveToken(token);
    }

    @DisplayName("WAITING 상태인 token 조회 시, 해당 토큰과 waiting number를 반환한다.")
    @Test
    void getQueueByToken_ShouldReturnWaitingQueue_WhenTokenIsWaiting() {
        // given
        String token = "waiting-token";
        int waitingNumber = 5;
        given(queueReader.isActiveToken(token)).willReturn(false);
        given(queueReader.isWaitingToken(token)).willReturn(true);
        given(queueReader.getWaitingNumber(token)).willReturn(waitingNumber);

        // when
        Queue queue = queueService.getQueueByToken(token);

        // then
        assertNotNull(queue);
        assertEquals(token, queue.getToken());
        assertEquals(Key.WAITING, queue.getKey());
        assertEquals(waitingNumber, queue.getWaitingNumber());
        then(queueReader).should().isActiveToken(token);
        then(queueReader).should().isWaitingToken(token);
        then(queueReader).should().getWaitingNumber(token);
    }

    @DisplayName("ACTIVE와 WAITING 상태도 아닌 찾을 수 없는 token 조회 시, 예외를 던진다.")
    @Test
    void getQueueByToken_ShouldThrowException_WhenTokenIsNotFound() {
        // given
        String token = "invalid-token";
        given(queueReader.isActiveToken(token)).willReturn(false);
        given(queueReader.isWaitingToken(token)).willReturn(false);

        // when & then
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            queueService.getQueueByToken(token);
        });

        assertEquals(TokenErrorCode.NOT_FOUND_TOKEN, exception.getErrorCode());
        then(queueReader).should().isActiveToken(token);
        then(queueReader).should().isWaitingToken(token);
    }

    @DisplayName("대기할 필요가 없으면 ACTIVE 토큰을 반환한다.")
    @Test
    void createNewQueue_ShouldReturnActiveQueue_WhenQueueIsAccessible() {
        // given
        given(queueReader.isAccessible()).willReturn(true);

        // when
        Queue queue = queueService.createNewQueue(UUID.randomUUID().toString(), System.currentTimeMillis());

        // then
        assertNotNull(queue);
        assertEquals(Key.ACTIVE, queue.getKey());
        then(queueReader).should().isAccessible();
        then(queueWriter).should().addActiveToken(queue);
        then(queueWriter).should().createActiveKey(queue, queueMonitor);
    }

    @DisplayName("대기해야하면 WAITING 토큰과 waiting number를 반환한다.")
    @Test
    void createNewQueue_ShouldReturnWaitingQueue_WhenQueueIsNotAccessible() {
        // given
        given(queueReader.isAccessible()).willReturn(false);
        doNothing().when(queueWriter).addWaitingToken(any(Queue.class));

        ArgumentCaptor<Queue> captor = ArgumentCaptor.forClass(Queue.class);
        given(queueReader.getWaitingNumber(anyString())).willReturn(1);

        // when
        Queue queue = queueService.createNewQueue(UUID.randomUUID().toString(), System.currentTimeMillis());

        // then
        assertNotNull(queue);
        assertEquals(Key.WAITING, queue.getKey());
        assertEquals(1L, queue.getWaitingNumber());
        then(queueReader).should().isAccessible();
        then(queueWriter).should().addWaitingToken(captor.capture());
        then(queueReader).should().getWaitingNumber(captor.getValue().getToken());
    }
}