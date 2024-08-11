package hhplus.concert.domain.queue.support;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemTokenFactory implements TokenFactory {
    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
