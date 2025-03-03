package hhplus.concert.core.queue.domain.service.support.monitor;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Component
public class SystemQueueMonitor implements QueueMonitor {

    private final long TIMEOUT = 600;
    private final TimeUnit TIME_UNIT = SECONDS;
    private final int MAX_ACTIVE_USER_COUNT = 50;

    @Override
    public Ttl getTtl() {
        return new Ttl(TIMEOUT, TIME_UNIT);
    }

    @Override
    public int getMaxActiveUserCount() {
        return MAX_ACTIVE_USER_COUNT;
    }
}
