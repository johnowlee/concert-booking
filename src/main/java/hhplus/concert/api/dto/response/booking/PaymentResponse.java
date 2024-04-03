package hhplus.concert.api.dto.response.booking;

import hhplus.concert.api.dto.response.ResponseResult;

public record PaymentResponse(ResponseResult paymentResult,
                              BookingResponse booking) {}
