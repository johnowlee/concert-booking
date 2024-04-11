package hhplus.concert.api.balance.dto.response;

import hhplus.concert.api.common.ResponseResult;

public record BalanceChargeResponse(ResponseResult chargeResult, long balance) {
    public static BalanceChargeResponse from(ResponseResult chargeResult, long balance) {
        return new BalanceChargeResponse(chargeResult, balance);
    }
}
