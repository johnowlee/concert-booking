package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.concert.dto.request.ConcertBookingRequest;
import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.model.Queue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookConcertUseCaseTest {

    @Autowired
    BookConcertUseCase bookConcertUseCase;
    @Autowired
    QueueReader queueReader;

    @DisplayName("콘서트 좌석 예약이 동시에 발생하면, 여러건 중 1건만 성공한다.")
    @Test
    void BookConcertJustOneCase_Success_ifConcurrencyOccurs() throws InterruptedException {
        // Given
        Queue processingQueue = queueReader.getProcessingQueueByUserId(1L);
        ConcertBookingRequest bookingRequest = ConcertBookingRequest.of(3L, "23,24");
        int numOfTask = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numOfTask);
        CountDownLatch latch = new CountDownLatch(numOfTask);
        AtomicInteger numOfBookingSuccess = new AtomicInteger(0);

        // When
        for (int i = 0; i < numOfTask; i++) {
            executor.submit(() -> {
                try {
                    bookConcertUseCase.execute(processingQueue.getId(), bookingRequest);
                    numOfBookingSuccess.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();

        // Then
        assertThat(numOfBookingSuccess).isEqualTo(1);
    }
}