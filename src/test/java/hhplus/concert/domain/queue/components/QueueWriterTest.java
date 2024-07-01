package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueuePolicy;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueueWriterTest {

    @InjectMocks
    QueueWriter queueWriter;

    @Mock
    QueueWriterRepository queueWriterRepository;

    @BeforeEach
    void setUp() {
        queueWriterRepository = Mockito.mock(QueueWriterRepository.class);
        queueWriter = new QueueWriter(queueWriterRepository);
    }

    @DisplayName("Set에 Active 대기열 토큰을 추가한다.")
    @Test
    void addActiveToken_Success() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.addActiveToken(queue);

        // then
        verify(queueWriterRepository, times(1)).addTokenToSet(any(Queue.class));
    }

    @DisplayName("Set에 Active 대기열 토큰을 제거 한다.")
    @Test
    void removeActiveToken_Success() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.removeActiveToken(queue);

        // then
        verify(queueWriterRepository, times(1)).removeTokenFromSet(any(Queue.class));
    }

    @DisplayName("Sorted Set에 Waiting 대기열 토큰을 추가 한다.")
    @Test
    void addWaitingToken_Success() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.addWaitingToken(queue);

        // then
        verify(queueWriterRepository, times(1)).addTokenToSortedSet(any(Queue.class));
    }

    @DisplayName("Sorted Set에 Waiting 대기열 토큰을 제거 한다.")
    @Test
    void removeWaitingToken_Success() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.removeWaitingToken(queue);

        // then
        verify(queueWriterRepository, times(1)).removeTokenFromSortedSet(any(Queue.class));
    }

    @DisplayName("Active Key 토큰을 생성 한다.")
    @Test
    void createActiveKey_Success() {
        // given
        String token = "ABC123";
        Queue queue = Queue.createActiveQueue(token);

        // when
        queueWriter.createActiveKey(queue);

        // then
        verify(queueWriterRepository, times(1))
                .createTimeoutKey(any(Queue.class), eq(QueuePolicy.MAX_WORKING_SEC.getValue()), eq(TimeUnit.SECONDS));
    }
}