package hhplus.concert.domain.queue.support.manager;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@Component
public class SystemTtlManager implements TtlManager {

    private final long TIMEOUT = 600;
    private final TimeUnit TIME_UNIT = SECONDS;

    @Override
    public Ttl getTtl() {
        return new Ttl(TIMEOUT, TIME_UNIT);
    }
}
