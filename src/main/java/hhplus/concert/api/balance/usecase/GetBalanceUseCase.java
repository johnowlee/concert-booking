package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.common.response.UserResponse;
import hhplus.concert.domain.user.components.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBalanceUseCase {

    private final UserReader userReader;

    public UserResponse execute(Long userId) {
        return UserResponse.forBalanceResponseFrom(userReader.getUserById(userId));
    }
}
