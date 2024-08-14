package hhplus.concert.api.balance.dto.response;

import hhplus.concert.domain.user.models.User;

public record BalanceResponse(Long id, String name, long balance) {
    public static BalanceResponse from(User user) {
        return new BalanceResponse(user.getId(), user.getName(), user.getBalance());
    }
}
