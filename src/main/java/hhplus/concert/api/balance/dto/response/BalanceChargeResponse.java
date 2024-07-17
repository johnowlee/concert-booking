package hhplus.concert.api.balance.dto.response;

import hhplus.concert.api.common.ResponseResult;

public record BalanceChargeResponse(ResponseResult chargeResult, long balance) {
    public static BalanceChargeResponse success(long balance) {
        return new BalanceChargeResponse(ResponseResult.SUCCESS, balance);
    }
}
