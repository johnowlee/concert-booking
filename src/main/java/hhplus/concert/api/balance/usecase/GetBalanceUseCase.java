package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.common.response.UserResponse;
import hhplus.concert.domain.user.components.UserReader;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
public class GetBalanceUseCase {

    private final UserReader userReader;

    public UserResponse execute(Long userId) {
        return UserResponse.from(userReader.getUserById(userId));
    }
}
