package hhplus.concert.domain.support;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemClockManager implements ClockManager {
    @Override
    public LocalDateTime getNowDateTime() {
        return LocalDateTime.now();
    }
}
