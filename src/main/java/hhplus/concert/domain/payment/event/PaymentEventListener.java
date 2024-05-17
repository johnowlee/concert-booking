package hhplus.concert.domain.payment.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private static final String PAYMENT_COMPLETE_TOPIC = "payments";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @EventListener(PaymentCompleteEvent.class)
    @Async
    public void handle(PaymentCompleteEvent event) {
        log.info("send message to kafka topic : {}, userId : {}, bookingId : {}, amount : {}", PAYMENT_COMPLETE_TOPIC, event.userId(), event.bookingId(), event.amount());

        try {
            kafkaTemplate.send(PAYMENT_COMPLETE_TOPIC, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.error("failed to send message to kafka topic : {}, userId : {}, bookingId : {}, amount : {}", PAYMENT_COMPLETE_TOPIC, event.userId(), event.bookingId(), event.amount());
        }
    }
}
