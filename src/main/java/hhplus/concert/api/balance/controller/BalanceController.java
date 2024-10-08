package hhplus.concert.api.balance.controller;

import hhplus.concert.api.balance.controller.request.BalanceChargeRequest;
import hhplus.concert.api.balance.usecase.ChargeBalanceUseCase;
import hhplus.concert.api.balance.usecase.GetBalanceUseCase;
import hhplus.concert.api.common.RestApiResponse;
import hhplus.concert.api.common.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final GetBalanceUseCase getBalanceUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;

    @GetMapping("{userId}")
    public RestApiResponse<UserResponse> balance(@PathVariable Long userId) {
        return RestApiResponse.ok(getBalanceUseCase.execute(userId));
    }

    @PatchMapping("{userId}")
    public RestApiResponse<UserResponse> charge(@PathVariable Long userId,
                                                   @Valid @RequestBody BalanceChargeRequest request) {
        return RestApiResponse.ok(chargeBalanceUseCase.execute(userId, request));
    }
}
