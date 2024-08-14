package hhplus.concert.domain.queue.support.monitor;

public interface QueueMonitor {

    Ttl getTtl();

    int getMaxActiveUserCount();

}
