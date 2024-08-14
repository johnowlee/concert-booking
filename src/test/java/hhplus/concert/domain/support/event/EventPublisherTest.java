package hhplus.concert.domain.support.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventPublisherTest {

    @Mock
    ApplicationEventPublisher publisher;

    @InjectMocks
    EventPublisher eventPublisher;

    @DisplayName("이벤트를 발행한다.")
    @Test
    void publish() {
        // given
        String event = "event";

        // when
        eventPublisher.publish(event);

        // then
        verify(publisher, times(1)).publishEvent("event");
    }

}