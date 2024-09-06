package hhplus.concert.domain.queue.event;

import hhplus.concert.domain.queue.service.RedisKeyEventService;
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

    private final RedisKeyEventService redisKeyEventService;

    public RedisKeyExpirationListener(RedisKeyEventService redisKeyEventService) {
        this.redisKeyEventService = redisKeyEventService;
    }

    @PostConstruct
    public void init() {
        redisMessageListenerContainer.addMessageListener(this, new ChannelTopic("__keyevent@0__:expired"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("Expired key={}", expiredKey);
        redisKeyEventService.expire(expiredKey);
    }
}
