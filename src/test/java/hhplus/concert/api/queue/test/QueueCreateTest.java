package hhplus.concert.api.queue.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.api.queue.usecase.FindTokenUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueueCreateTest {


    @Autowired
    FindTokenUseCase findTokenUseCase;
    @Autowired
    CreateTokenUseCase createTokenUseCase;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void create() throws JsonProcessingException {
        for (int i = 0; i < 55; i++) {
            QueueResponse q = createTokenUseCase.execute();
            System.out.println(" q : " + objectMapper.writeValueAsString(q));
        }
    }

    @Test
    void find() throws JsonProcessingException {
        QueueResponse execute = findTokenUseCase.execute("f261b318-553f-4347-aa8c-f80ada3f7c38");
        System.out.println(" q : " + objectMapper.writeValueAsString(execute));
    }
}
