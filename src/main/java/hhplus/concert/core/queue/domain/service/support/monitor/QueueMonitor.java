package hhplus.concert.core.queue.domain.service.support.monitor;

public interface QueueMonitor {

    Ttl getTtl();

    int getMaxActiveUserCount();

}
