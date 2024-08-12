package hhplus.concert.domain.queue.support.monitor;

import java.util.concurrent.TimeUnit;

public record Ttl(long timeout, TimeUnit timeUnit) {
}
