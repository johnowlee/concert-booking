package hhplus.concert.domain.queue.service;

import hhplus.concert.domain.queue.components.QueueReader;
import hhplus.concert.domain.queue.components.QueueWriter;
import hhplus.concert.domain.queue.model.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisKeyEventService {

    private final QueueReader queueReader;
    private final QueueWriter queueWriter;

    public void expire(String token) {
        removeTokenFromActive(token);
        moveFirstWaiterToActive();
    }

    private void moveFirstWaiterToActive() {
        Set<String> result = queueReader.getFirstWaiter();
        if (result != null && !result.isEmpty()) {
            String token = result.iterator().next();// 첫 번째 요소 반환
            removeTokenFromWaiting(token);// waiting 제거
            addTokenToActive(token);// active로 추가
            setTokenAsActiveUser(token);// 만료시간 관리용 key 추가
        }
    }

    private void removeTokenFromActive(String token) {
        queueWriter.removeActiveToken(Queue.createActiveQueue(token));
    }

    private void removeTokenFromWaiting(String token) {
        queueWriter.removeWaitingToken(Queue.createWaitingQueue(token, null));
    }

    private void addTokenToActive(String token) {
        queueWriter.addActiveToken(Queue.createActiveQueue(token));
    }

    private void setTokenAsActiveUser(String token) {
        queueWriter.createActiveKey(Queue.createActiveQueue(token));
    }
}
