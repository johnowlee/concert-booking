package hhplus.concert.api.balance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.concert.api.balance.dto.request.BalanceChargeRequest;
import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.api.balance.dto.response.BalanceResponse;
import hhplus.concert.api.balance.usecase.ChargeBalanceUseCase;
import hhplus.concert.api.balance.usecase.GetBalanceUseCase;
import hhplus.concert.api.common.ResponseResult;
import hhplus.concert.domain.queue.service.TokenValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
    private TokenValidator tokenValidator;

    @DisplayName("포인트 조회")
    @Test
    public void getBalance() throws Exception {
        // given
        BalanceResponse balanceResponse = BalanceResponse.from(10000);
        given(getBalanceUseCase.execute(anyLong())).willReturn(balanceResponse);

        // expected
        mockMvc.perform(get("/balance/{userId}", 1)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(10000));
    }

    @DisplayName("포인트 충전")
    @Test
    public void chargeBalance() throws Exception {
        // given
        BalanceChargeResponse balanceChargeResponse = BalanceChargeResponse.success(10000);
        given(chargeBalanceUseCase.execute(anyLong(), anyLong())).willReturn(balanceChargeResponse);

        // expected
        mockMvc.perform(patch("/balance/{userId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new BalanceChargeRequest(10000)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(10000))
                .andExpect(jsonPath("$.chargeResult").value(ResponseResult.SUCCESS.name()));
    }
}