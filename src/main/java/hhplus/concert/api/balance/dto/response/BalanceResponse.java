package hhplus.concert.api.balance.dto.response;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE, force = true)
public record BalanceResponse(long balance) {
    public static BalanceResponse from(long balance) {
        return new BalanceResponse(balance);
    }
}
