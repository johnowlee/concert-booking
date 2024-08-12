package hhplus.concert.domain.queue.support.manager;

import java.util.concurrent.TimeUnit;

public record Ttl(long timeout, TimeUnit timeUnit) {
}
