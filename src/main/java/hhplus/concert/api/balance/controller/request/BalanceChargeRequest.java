package hhplus.concert.api.balance.controller.request;

import jakarta.validation.constraints.Positive;

public record BalanceChargeRequest(@Positive(message = "충전금액은 양수이어야 합니다.") long balance) {
}
