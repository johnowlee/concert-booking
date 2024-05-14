package hhplus.concert.api.queue.usecase;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.queue.dto.response.TokenResponse;
import hhplus.concert.domain.queue.model.Key;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static hhplus.concert.api.exception.code.TokenErrorCode.NOT_FOUND_TOKEN;
import static hhplus.concert.api.queue.dto.response.TokenResponse.createActiveTokenResponse;
import static hhplus.concert.api.queue.dto.response.TokenResponse.createWaitingTokenResponse;
import static hhplus.concert.domain.queue.model.QueuePolicy.MAX_CONCURRENT_USERS;
import static hhplus.concert.domain.queue.model.QueuePolicy.MAX_WORKING_SEC;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisQueueService {

    private final RedisTemplate<String, String> redis;

    public TokenResponse createQueueToken() {
        String token = UUID.randomUUID().toString();

        if (isAccessible()) {
            addTokenToActive(token);
            setTokenAsActiveUser(token);
            return createActiveTokenResponse(token, Key.ACTIVE);
        } else {
            addTokenToWaiting(token);
            return createWaitingTokenResponse(token, Key.WAITING, getWaitingNumber(token));
        }
    }

    public TokenResponse findToken(String token) {
        if (isActiveToken(token)) return createActiveTokenResponse(token, Key.ACTIVE);
        if (isWaitingToken(token)) return createWaitingTokenResponse(token, Key.WAITING, getWaitingNumber(token));
        throw new RestApiException(NOT_FOUND_TOKEN);
    }

    public void expire(String token) {
        removeTokenFromActive(token);
        moveFirstWaiterToActive();
    }

    private void moveFirstWaiterToActive() {
        Set<String> result = getFirstWaiter();
        if (result != null && !result.isEmpty()) {
            String firstWaiter = result.iterator().next();// 첫 번째 요소 반환
            removeTokenFromWaiting(firstWaiter);// waiting 제거
            addTokenToActive(firstWaiter);// active로 추가
            setTokenAsActiveUser(firstWaiter);// 만료시간 관리용 key 추가
        }
    }

    public void addTokenToActive(String token) {
        redis.opsForSet().add(Key.ACTIVE.toString(), token);
    }

    public void removeTokenFromActive(String token) {
        redis.opsForSet().remove(Key.ACTIVE.toString(), token);
    }

    public void addTokenToWaiting(String token) {
        redis.opsForZSet().add(Key.WAITING.toString(), token, System.currentTimeMillis());
    }

    public void removeTokenFromWaiting(String firstWaiter) {
        redis.opsForZSet().remove(Key.WAITING.toString(), firstWaiter);
    }

    public void setTokenAsActiveUser(String token) {
        redis.opsForValue().set(token, "active", MAX_WORKING_SEC.getValue(), TimeUnit.SECONDS);
    }

    private boolean isAccessible() {
        Long concurrentSize = redis.opsForSet().size(Key.ACTIVE.toString());
        return concurrentSize == null || concurrentSize < MAX_CONCURRENT_USERS.getValue();
    }

    public Set<String> getFirstWaiter() {
        return redis.opsForZSet().range(Key.WAITING.toString(), 0, 0);
    }

    public Long getWaitingNumber(String token) {
        // 사용자의 대기 순번 조회
        Long rank = redis.opsForZSet().rank(Key.WAITING.toString(), token);
        return rank != null ? rank + 1 : null; // 순번은 1부터 시작하므로 1을 더해줌
    }

    public Boolean isActiveToken(String token) {
        return redis.opsForSet().isMember(Key.ACTIVE.toString(), token);
    }

    public boolean isWaitingToken(String token) {
        return redis.opsForZSet().score(Key.WAITING.toString(), token) != null;
    }
}
