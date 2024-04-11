package hhplus.concert.api.balance.controller;

import hhplus.concert.api.balance.dto.request.BalanceChargeRequest;
import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.api.balance.dto.response.BalanceResponse;
import hhplus.concert.api.balance.usecase.ChargeBalanceUseCase;
import hhplus.concert.api.balance.usecase.GetBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final GetBalanceUseCase getBalanceUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;

    @GetMapping("{userId}")
    public BalanceResponse balance(@PathVariable Long userId) {
        return getBalanceUseCase.excute(userId);
    }

    @PostMapping("{userId}")
    public BalanceChargeResponse charge(@PathVariable Long userId, @RequestBody BalanceChargeRequest balanceChargeRequest) {
        return chargeBalanceUseCase.excute(userId, balanceChargeRequest.balance());
    }
}
