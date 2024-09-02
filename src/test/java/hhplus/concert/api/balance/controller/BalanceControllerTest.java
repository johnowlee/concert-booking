package hhplus.concert.api.balance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.balance.controller.request.BalanceChargeRequest;
import hhplus.concert.api.balance.usecase.ChargeBalanceUseCase;
import hhplus.concert.api.balance.usecase.GetBalanceUseCase;
import hhplus.concert.api.common.response.UserResponse;
import hhplus.concert.domain.queue.service.QueueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BalanceController.class)
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private GetBalanceUseCase getBalanceUseCase;

    @MockBean
    private ChargeBalanceUseCase chargeBalanceUseCase;

    @MockBean
    private QueueService queueService;

    @DisplayName("잔액을 조회한다.")
    @Test
    public void getBalance() throws Exception {
        // given
        Long userId = 1L;
        String name = "jon";
        long balance = 10000L;
        UserResponse balanceResponse = new UserResponse(userId, name, balance, null, null);
        given(getBalanceUseCase.execute(anyLong())).willReturn(balanceResponse);

        // when & then
        mockMvc.perform(get("/balance/{userId}", 1)
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("jon"))
                .andExpect(jsonPath("$.data.balance").value(10000));
    }

    @DisplayName("잔액을 충전한다.")
    @Test
    void chargeBalance() throws Exception {
        // given
        Long userId = 1L;
        String name = "jon";
        long balance = 10000L;
        BalanceChargeRequest chargeRequest = new BalanceChargeRequest(30000L);
        UserResponse balanceResponse = new UserResponse(userId, name, balance + chargeRequest.balance(), null, null);
        given(chargeBalanceUseCase.execute(userId, chargeRequest)).willReturn(balanceResponse);

        // when & then
        mockMvc.perform(patch("/balance/{userId}", userId)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chargeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.name").value("jon"))
                .andExpect(jsonPath("$.data.balance").value(10000L + 30000L));
    }

    @DisplayName("포인트를 충전할 떄 충전 금액은 양수이다.")
    @Test
    void chargeBalanceWithZeroPoint() throws Exception {
        // given
        BalanceChargeRequest request = new BalanceChargeRequest(0);

        // when & then
        mockMvc.perform(patch("/balance/{userId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("충전금액은 양수이어야 합니다."));
    }
}