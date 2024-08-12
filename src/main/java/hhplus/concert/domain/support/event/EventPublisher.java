package hhplus.concert.domain.support.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publish(Object event) {
        publisher.publishEvent(event);
    }
}
