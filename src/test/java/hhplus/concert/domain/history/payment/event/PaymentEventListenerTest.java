package hhplus.concert.domain.history.payment.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentEventListenerTest {

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    PaymentEventListener paymentEventListener;

    @DisplayName("Payment 정보를 카프카 서버로 메시지를 전송한다.")
    @Test
    void handle() throws JsonProcessingException {
        // given
        PaymentCompletion event = new PaymentCompletion(1L, 2L, 3000L);
        String message = new ObjectMapper().writeValueAsString(event);

        given(objectMapper.writeValueAsString(event)).willReturn(message);

        // when
        paymentEventListener.handle(event);

        // then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(eq("payments"), captor.capture());
        assertThat(captor.getValue()).isEqualTo(message);
    }
}