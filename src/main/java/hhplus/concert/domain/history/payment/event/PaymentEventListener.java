package hhplus.concert.domain.history.payment.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private static final String PAYMENT_COMPLETE_TOPIC = "payments";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

//    @EventListener(PaymentCompletion.class)
//    @Async
    public void handle(PaymentCompletion event) {
        log.info("send message to kafka topic : {}, userId : {}, bookingId : {}, amount : {}", PAYMENT_COMPLETE_TOPIC, event.payerId(), event.bookingId(), event.paymentAmount());

        try {
            kafkaTemplate.send(PAYMENT_COMPLETE_TOPIC, objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.error("failed to send message to kafka topic : {}, userId : {}, bookingId : {}, amount : {}", PAYMENT_COMPLETE_TOPIC, event.payerId(), event.bookingId(), event.paymentAmount());
        }
    }
}
