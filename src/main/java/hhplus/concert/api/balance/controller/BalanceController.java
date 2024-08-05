package hhplus.concert.api.balance.controller;

import hhplus.concert.api.balance.dto.request.BalanceChargeRequest;
import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.api.balance.dto.response.BalanceResponse;
import hhplus.concert.api.balance.usecase.ChargeBalanceUseCase;
import hhplus.concert.api.balance.usecase.GetBalanceUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final GetBalanceUseCase getBalanceUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;

    @GetMapping("{userId}")
    public ResponseEntity<BalanceResponse> balance(@PathVariable Long userId) {
        return ResponseEntity.ok().body(getBalanceUseCase.execute(userId));
    }

    @PatchMapping("{userId}")
    public ResponseEntity<BalanceChargeResponse> charge(@PathVariable Long userId,
                                                        @Valid @RequestBody BalanceChargeRequest balanceChargeRequest) {
        return ResponseEntity.ok().body(chargeBalanceUseCase.execute(userId, balanceChargeRequest.balance()));
    }
}
