package hhplus.concert.domain.queue.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hhplus.concert.domain.queue.model.Key.*;
import static org.assertj.core.api.Assertions.assertThat;

class QueueTest {

    @DisplayName("ACTIVE Queue 객체를 생성한다.")
    @Test
    void createActiveQueue() {
        // given
        String token = "ABC123";

        // when
        Queue queue = Queue.createActiveQueue(token);

        // then
        assertThat(queue.getToken()).isEqualTo("ABC123");
        assertThat(queue.getKey()).isEqualTo(ACTIVE);
        assertThat(queue.getWaitingNumber()).isZero();
        assertThat(queue.getScore()).isZero();
    }

    @DisplayName("WAITING Queue 객체를 생성한다.")
    @Test
    void createWaitingQueue() {
        // given
        String token = "ABC123";

        // when
        Queue queue = Queue.createWaitingQueue(token);

        // then
        assertThat(queue.getToken()).isEqualTo("ABC123");
        assertThat(queue.getKey()).isEqualTo(WAITING);
        assertThat(queue.getWaitingNumber()).isZero();
        assertThat(queue.getScore()).isZero();
    }

    @DisplayName("대기번호가 있는 WAITING Queue 객체를 생성한다.")
    @Test
    void createWaitingQueueWithWaitingNumber() {
        // given
        String token = "ABC123";
        int waitingNumber = 20;

        // when
        Queue queue = Queue.createWaitingQueue(token, waitingNumber);

        // then
        assertThat(queue.getToken()).isEqualTo("ABC123");
        assertThat(queue.getKey()).isEqualTo(WAITING);
        assertThat(queue.getWaitingNumber()).isEqualTo(20);
        assertThat(queue.getScore()).isZero();
    }

    @DisplayName("score가 있는 ACTIVE Queue 객체를 생성한다.")
    @Test
    void createNewWaitingQueue() {
        // given
        String token = "ABC123";
        long score = 1239485;

        // when
        Queue queue = Queue.createWaitingQueue(token, score);

        // then
        assertThat(queue.getToken()).isEqualTo("ABC123");
        assertThat(queue.getKey()).isEqualTo(WAITING);
        assertThat(queue.getWaitingNumber()).isZero();
        assertThat(queue.getScore()).isEqualTo(1239485);
    }

}