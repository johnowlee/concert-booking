package hhplus.concert.api.queue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.queue.dto.response.QueueResponse;
import hhplus.concert.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.api.queue.usecase.FindTokenUseCase;
import hhplus.concert.domain.queue.model.Key;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.support.TokenValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QueueController.class)
class QueueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FindTokenUseCase findTokenUseCase;

    @MockBean
    CreateTokenUseCase createQueueToken;

    @MockBean
    private TokenValidator tokenValidator;

    @DisplayName("토큰 조회 - ACTIVE")
    @Test
    public void findToken_active() throws Exception {
        // given
        String token = "token";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createActiveQueue(token));
        given(findTokenUseCase.execute(token)).willReturn(queueResponse);

        // expected
        mockMvc.perform(get("/queue")
                        .contentType(APPLICATION_JSON)
                        .header("Queue-Token", token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.key").value(Key.ACTIVE.toString()));
    }

    @DisplayName("토큰 조회 - WAITING")
    @Test
    public void findToken_waiting() throws Exception {
        // given
        String token = "token";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createWaitingQueue(token, 5));
        given(findTokenUseCase.execute(token)).willReturn(queueResponse);

        // expected
        mockMvc.perform(get("/queue")
                        .contentType(APPLICATION_JSON)
                        .header("Queue-Token", token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.key").value(Key.WAITING.toString()))
                .andExpect(jsonPath("$.waitingNumber").value(5L));
    }

    @DisplayName("토큰 생성 - ACTIVE")
    @Test
    public void createToken_active() throws Exception {
        // given
        String token = "token";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createActiveQueue(token));
        given(createQueueToken.execute()).willReturn(queueResponse);

        // expected
        mockMvc.perform(post("/queue")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.key").value(Key.ACTIVE.toString()));
    }

    @DisplayName("토큰 생성 - WAITING")
    @Test
    public void createToken_waiting() throws Exception {
        // given
        String token = "token";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createWaitingQueue(token, 5));
        given(createQueueToken.execute()).willReturn(queueResponse);

        // expected
        mockMvc.perform(post("/queue")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.key").value(Key.WAITING.toString()))
                .andExpect(jsonPath("$.waitingNumber").value(5L));
    }
}