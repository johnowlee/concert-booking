package hhplus.concert.representer.api.queue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.representer.api.queue.controller.QueueController;
import hhplus.concert.representer.api.queue.controller.request.QueueTokenRequest;
import hhplus.concert.representer.api.queue.usecase.CreateTokenUseCase;
import hhplus.concert.representer.api.queue.usecase.FindTokenUseCase;
import hhplus.concert.representer.api.queue.usecase.response.QueueResponse;
import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.service.QueueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private QueueService queueService;

    @DisplayName("ACTIVE 토큰을 조회한다.")
    @Test
    public void findTokenWithActiveToken() throws Exception {
        // given
        String token = "abc123";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createActiveQueue(token));
        given(findTokenUseCase.execute(new QueueTokenRequest(token))).willReturn(queueResponse);

        // when & then
        mockMvc.perform(get("/queue")
                        .contentType(APPLICATION_JSON)
                        .header("Queue-Token", token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.token").value("abc123"))
                .andExpect(jsonPath("$.data.key").value("ACTIVE"));
    }

    @DisplayName("WAITING 토큰을 조회한다.")
    @Test
    public void findTokenWithWaitingToken() throws Exception {
        // given
        String token = "abc123";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createWaitingQueue(token, 5));
        given(findTokenUseCase.execute(new QueueTokenRequest(token))).willReturn(queueResponse);

        // when & then
        mockMvc.perform(get("/queue")
                        .contentType(APPLICATION_JSON)
                        .header("Queue-Token", token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.token").value("abc123"))
                .andExpect(jsonPath("$.data.key").value("WAITING"))
                .andExpect(jsonPath("$.data.waitingNumber").value(5));
    }

    @DisplayName("ACTIVE 토큰을 생성한다.")
    @Test
    public void createTokenWhenActive() throws Exception {
        // given
        String token = "abc123";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createActiveQueue(token));
        given(createQueueToken.execute()).willReturn(queueResponse);

        // when & then
        mockMvc.perform(post("/queue")
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.token").value("abc123"))
                .andExpect(jsonPath("$.data.key").value("ACTIVE"));
    }

    @DisplayName("WAITING 토큰을 생성한다.")
    @Test
    public void createTokenWhenWaiting() throws Exception {
        // given
        String token = "abc123";
        QueueResponse queueResponse = QueueResponse.createQueueResponse(Queue.createWaitingQueue(token, 5));
        given(createQueueToken.execute()).willReturn(queueResponse);

        // when & then
        mockMvc.perform(post("/queue")
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.token").value("abc123"))
                .andExpect(jsonPath("$.data.key").value("WAITING"))
                .andExpect(jsonPath("$.data.waitingNumber").value(5));
    }
}