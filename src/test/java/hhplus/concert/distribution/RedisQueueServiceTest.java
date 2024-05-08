package hhplus.concert.distribution;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class NewRedisQueueServiceTest {

    @Autowired
    NewRedisQueueService redisQueueService;

    @Autowired
    RedisKeyExpirationListener redisKeyExpirationListener;



    @Test
    void tryAccess() {
        for (int i = 0; i < 10; i++) {
            AccessToken accessToken = redisQueueService.tryAccess();
            System.out.println(" token = " + accessToken.token());
            System.out.println(" key = " + accessToken.key());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }
    }

    @Test
    void getPosition() {
        Set<String> firstWaiter = redisQueueService.getFirstWaiter();
        String next = firstWaiter.iterator().next();
        System.out.println(" getFirstWaiter = " + firstWaiter.iterator().next());

        Long queuePosition2 = redisQueueService.getQueuePosition("397ca196-e5a3-45c4-9266-53405727ffc3");
        System.out.println(" queuePosition 88 = " + queuePosition2);
    }
}