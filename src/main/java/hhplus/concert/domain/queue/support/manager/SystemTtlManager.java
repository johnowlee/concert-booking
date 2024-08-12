package hhplus.concert.domain.queue.support.manager;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SystemTtlManager implements TtlManager {

    private final long timeout = 600;
    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    @Override
    public Ttl getTtl() {
        return new Ttl(timeout, timeUnit);
    }
}
