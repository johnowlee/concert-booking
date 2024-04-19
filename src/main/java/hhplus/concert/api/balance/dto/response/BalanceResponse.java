package hhplus.concert.api.balance.dto.response;

public record BalanceResponse(long balance) {
    public static BalanceResponse from(long balance) {
        return new BalanceResponse(balance);
    }
}
