package hhplus.concert.core.queue.domain.service.support.monitor;

import java.util.concurrent.TimeUnit;

public record Ttl(long timeout, TimeUnit timeUnit) {
}
