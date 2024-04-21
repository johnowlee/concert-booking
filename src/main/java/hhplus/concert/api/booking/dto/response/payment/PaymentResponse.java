package hhplus.concert.api.booking.dto.response.payment;

import hhplus.concert.api.common.ResponseResult;

public record PaymentResponse(ResponseResult paymentResult) {
    public static PaymentResponse from(ResponseResult paymentResult) {
        return new PaymentResponse(paymentResult);
    }
}
