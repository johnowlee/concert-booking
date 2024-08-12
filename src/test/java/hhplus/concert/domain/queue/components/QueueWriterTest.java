package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import hhplus.concert.domain.queue.support.manager.Ttl;
import hhplus.concert.domain.queue.support.manager.TtlManager;
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
class QueueWriterTest {

    @InjectMocks
    QueueWriter queueWriter;

    @Mock
    QueueWriterRepository queueWriterRepository;

    @Mock
    TtlManager ttlManager;

    @DisplayName("활성 유저를 활성 Key Set에 추가한다.")
    @Test
    void addActiveToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.addActiveToken(queue);

        // then
        verify(queueWriterRepository, times(1)).addUserToActiveSet(eq(queue));
    }

    @DisplayName("활성 유저를 활성 Key Set에서 삭제한다.")
    @Test
    void removeActiveToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.removeActiveToken(queue);

        // then
        verify(queueWriterRepository, times(1)).removeUserFromActiveSet(eq(queue));
    }

    @DisplayName("대기 유저를 대기 Key SortedSet에 추가한다.")
    @Test
    void addWaitingToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createWaitingQueue(token);

        // when
        queueWriter.addWaitingToken(queue);

        // then
        verify(queueWriterRepository, times(1)).addUserToWaitingSortedSet(eq(queue));
    }

    @DisplayName("대기 유저를 대기 Key SortedSet에서 삭제한다.")
    @Test
    void removeWaitingToken() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createWaitingQueue(token);

        // when
        queueWriter.removeWaitingToken(queue);

        // then
        verify(queueWriterRepository, times(1)).removeUserFromWaitingSortedSet(eq(queue));
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
        given(ttlManager.getTtl()).willReturn(ttl);

        // when
        queueWriter.createActiveKey(queue, ttlManager);

        // then
        verify(queueWriterRepository, times(1))
                .createUserTimeout(eq(queue), eq(300L), eq(SECONDS));
    }
}