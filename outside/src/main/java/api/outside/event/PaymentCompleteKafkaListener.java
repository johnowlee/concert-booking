package api.outside.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCompleteKafkaListener {

    @KafkaListener(topics = "payments", groupId = "group_1")
    public void listener(ConsumerRecord<String, String> data) {
        log.info("get message : {}", data);
        try {
            succeed();
            fail();
        } catch (Exception e) {
            log.info("e : {}", e.getMessage());
        }
    }

    private static void succeed() {
        log.info("결제정보 저장 성공");
    }

    private static void fail() {
        log.info("결제정보 저장 실패");
        throw new RuntimeException("처리에러");
    }
}
