package hhplus.concert.core.queue.components;

import hhplus.concert.core.queue.domain.model.Queue;
import hhplus.concert.core.queue.domain.port.QueueCommandPort;
import hhplus.concert.core.queue.domain.service.QueueCommandService;
import hhplus.concert.core.queue.domain.service.support.monitor.Ttl;
import hhplus.concert.core.queue.domain.service.support.monitor.QueueMonitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueueCommandServiceTest {

    @InjectMocks
    QueueCommandService queueCommandService;

    @Mock
    QueueCommandPort queueCommandPort;

    @Mock
    QueueMonitor queueMonitor;

    @DisplayName("활성 유저를 활성 Key Set에 추가한다.")
    @Test
    void addActiveToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueCommandService.addActiveToken(queue);

        // then
        verify(queueCommandPort, times(1)).addTokenToSet(eq(queue));
    }

    @DisplayName("활성 유저를 활성 Key Set에서 삭제한다.")
    @Test
    void removeActiveToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueCommandService.removeActiveToken(queue);

        // then
        verify(queueCommandPort, times(1)).removeTokenFromSet(eq(queue));
    }

    @DisplayName("대기 유저를 대기 Key SortedSet에 추가한다.")
    @Test
    void addWaitingToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createWaitingQueue(token);

        // when
        queueCommandService.addWaitingToken(queue);

        // then
        verify(queueCommandPort, times(1)).addTokenToSortedSet(eq(queue));
    }

    @DisplayName("대기 유저를 대기 Key SortedSet에서 삭제한다.")
    @Test
    void removeWaitingToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createWaitingQueue(token);

        // when
        queueCommandService.removeWaitingToken(queue);

        // then
        verify(queueCommandPort, times(1)).removeTokenFromSortedSet(eq(queue));
    }

    @DisplayName("유저의 토큰값으로 유효기간이 있는 key를 등록한다.")
    @Test
    void createActiveKey() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        long timeout = 300L;
        TimeUnit timeUnit = SECONDS;
        Ttl ttl = new Ttl(timeout, timeUnit);
        given(queueMonitor.getTtl()).willReturn(ttl);

        // when
        queueCommandService.setActiveTokenTtl(queue, queueMonitor);

        // then
        verify(queueCommandPort, times(1))
                .createTtlToken(eq(queue), eq(300L), eq(SECONDS));
    }
}