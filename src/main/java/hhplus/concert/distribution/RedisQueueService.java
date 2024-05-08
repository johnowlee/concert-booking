package hhplus.concert.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class NewRedisQueueService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String ACTIVE_KEY = "active";
    private static final String WAITING_KEY = "waiting";
    private static final int MAX_CONCURRENT_USERS = 50;
    private static final long MAX_WORKING_SEC = 20; // 10분

    public AccessToken tryAccess() {
        String token = UUID.randomUUID().toString();
        Long currentSize = redisTemplate.opsForSet().size(ACTIVE_KEY);

        if (currentSize == null || currentSize < MAX_CONCURRENT_USERS) {
            // 사용자 접속 허용 및 TTL 설정
            redisTemplate.opsForSet().add(ACTIVE_KEY, token);

            // active 사용자별로 key 생성(만료시간체크용)
            redisTemplate.opsForValue().set(token, "activeUsers", MAX_WORKING_SEC, TimeUnit.SECONDS);

            return new AccessToken(token, ACTIVE_KEY); // 접속 성공
        } else {
            // 대기열에 추가
            redisTemplate.opsForZSet().add(WAITING_KEY, token, System.currentTimeMillis());
            return new AccessToken(token, WAITING_KEY); // 대기열로 이동
        }
    }

    public void releaseAccess(Long userId) {
        // 접속 종료 시 사용자 제거
        redisTemplate.opsForZSet().remove(WAITING_KEY, userId);
    }

    public void finishWorking(String token) {
        // 작업완료 시 Active 사용자 제거
        redisTemplate.opsForSet().remove(ACTIVE_KEY, token);

        //
        // Waiting 첫번째 대기자 제거 및 제거된 대기자 ACTIVE
        Set<String> result = getFirstWaiter();
        if (result != null && !result.isEmpty()) {
            String firstWaiter = result.iterator().next();// 첫 번째 요소 반환
            redisTemplate.opsForZSet().remove(WAITING_KEY, firstWaiter);// waiting 제거
            redisTemplate.opsForSet().add(ACTIVE_KEY, firstWaiter);// active
        }
    }

    public Set<String> getFirstWaiter() {
        return redisTemplate.opsForZSet().range(WAITING_KEY, 0, 0);
    }


    public Long getQueuePosition(String token) {
        // 사용자의 대기 순번 조회
        Long rank = redisTemplate.opsForZSet().rank(WAITING_KEY, token);
        return rank != null ? rank + 1 : null; // 순번은 1부터 시작하므로 1을 더해줌
    }
}
