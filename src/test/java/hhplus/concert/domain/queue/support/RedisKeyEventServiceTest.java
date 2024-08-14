package hhplus.concert.domain.queue.support;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RedisKeyEventServiceTest extends IntegrationTestSupport {

    @MockBean
    QueueReader queueReader;

    @MockBean
    QueueWriter queueWriter;

    @MockBean
    QueueMonitor queueMonitor;

    @Autowired
    RedisKeyEventService redisKeyEventService;

    @DisplayName("토큰을 만료하고 대기열에 대기자가 있으면 첫번째 대기자를 활성 유저로 추가한다.")
    @Test
    void expire() {
        // given
        String removeToken = "abc";
        Set<String> firstWaiter = Set.of("qwer");

        given(queueReader.getFirstWaiter()).willReturn(firstWaiter);

        // when
        redisKeyEventService.expire(removeToken);

        // then
        verify(queueWriter, times(1)).removeActiveToken(any(Queue.class));
        verify(queueWriter, times(1)).removeWaitingToken(any(Queue.class));
        verify(queueWriter, times(1)).addActiveToken(any(Queue.class));
        verify(queueWriter, times(1)).createActiveKey(any(Queue.class), eq(queueMonitor));
    }

    @DisplayName("활성 유저의 토큰을 만료하고 만약 대기열에 대기자가 없으면 아무일도 일어나지 않는다.")
    @Test
    void expireWhenNoWaiter() {
        // given
        String removeToken = "abc";
        Set<String> firstWaiter = null;

        given(queueReader.getFirstWaiter()).willReturn(firstWaiter);

        // when
        redisKeyEventService.expire(removeToken);

        // then
        verify(queueWriter, times(1)).removeActiveToken(any(Queue.class));
        verify(queueWriter, times(0)).removeWaitingToken(any(Queue.class));
        verify(queueWriter, times(0)).addActiveToken(any(Queue.class));
        verify(queueWriter, times(0)).createActiveKey(any(Queue.class), eq(queueMonitor));
    }
}