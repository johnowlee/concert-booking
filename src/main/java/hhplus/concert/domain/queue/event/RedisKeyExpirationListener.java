package hhplus.concert.domain.queue.event;

import hhplus.concert.api.queue.usecase.RedisQueueService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisKeyExpirationListener implements MessageListener {
    @Resource
    private RedisMessageListenerContainer redisMessageListenerContainer;

    private final RedisQueueService redisQueueService;

    public RedisKeyExpirationListener(RedisQueueService redisQueueService) {
        this.redisQueueService = redisQueueService;
    }

    @PostConstruct
    public void init() {
        // "__keyevent@0__:expired"는 디폴트 Redis 인스턴스의 만료 이벤트를 수신합니다.
        redisMessageListenerContainer.addMessageListener(this, new ChannelTopic("__keyevent@0__:expired"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("Expired key={}", expiredKey);
        // 여기에서 만료된 키에 대한 로직을 처리합니다.
        redisQueueService.expire(expiredKey);
    }
}
