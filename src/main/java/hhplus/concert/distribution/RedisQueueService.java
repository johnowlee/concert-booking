package hhplus.concert.distribution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static hhplus.concert.distribution.QueuePolicy.*;
import static hhplus.concert.distribution.TokenResponse.*;
import static hhplus.concert.distribution.TokenKey.*;

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
            return createActiveTokenResponse(token, ACTIVE_KEY);
        } else {
            addTokenToWaiting(token);
            return createWaitingTokenResponse(token, WAITING_KEY, getWaitingNumber(token));
        }
    }

    public TokenResponse findToken(String token) {
        if (isActiveToken(token)) {
            return createActiveTokenResponse(token, ACTIVE_KEY);
        } else {
            return createWaitingTokenResponse(token, WAITING_KEY, getWaitingNumber(token));
        }
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
            addTokenToActive(firstWaiter);
        }
    }

    public void addTokenToActive(String token) {
        redis.opsForSet().add(ACTIVE_KEY.getValue(), token);
    }

    public void removeTokenFromActive(String token) {
        redis.opsForSet().remove(ACTIVE_KEY.getValue(), token);
    }

    public void addTokenToWaiting(String token) {
        redis.opsForZSet().add(WAITING_KEY.getValue(), token, System.currentTimeMillis());
    }

    public void removeTokenFromWaiting(String firstWaiter) {
        redis.opsForZSet().remove(WAITING_KEY.getValue(), firstWaiter);
    }

    public void setTokenAsActiveUser(String token) {
        redis.opsForValue().set(token, "active", MAX_WORKING_SEC.getValue(), TimeUnit.SECONDS);
    }

    private boolean isAccessible() {
        Long concurrentSize = redis.opsForSet().size(ACTIVE_KEY.getValue());
        return concurrentSize == null || concurrentSize < MAX_CONCURRENT_USERS.getValue();
    }

    public Set<String> getFirstWaiter() {
        return redis.opsForZSet().range(WAITING_KEY.getValue(), 0, 0);
    }

    public Long getWaitingNumber(String token) {
        // 사용자의 대기 순번 조회
        Long rank = redis.opsForZSet().rank(WAITING_KEY.getValue(), token);
        return rank != null ? rank + 1 : null; // 순번은 1부터 시작하므로 1을 더해줌
    }

    public boolean isActiveToken(String token) {
        return redis.hasKey(token);
    }
}
