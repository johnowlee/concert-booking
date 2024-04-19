package hhplus.concert.api.balance.dto.response;

import hhplus.concert.api.common.ResponseResult;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE, force = true)
public record BalanceChargeResponse(ResponseResult chargeResult, long balance) {
    public static BalanceChargeResponse from(ResponseResult chargeResult, long balance) {
        return new BalanceChargeResponse(chargeResult, balance);
    }
}
