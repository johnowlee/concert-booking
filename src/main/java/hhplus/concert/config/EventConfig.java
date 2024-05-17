package hhplus.concert.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    private final ApplicationEventPublisher publisher;

    public EventConfig(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
