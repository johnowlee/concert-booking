package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.dto.response.BalanceResponse;
import hhplus.concert.domain.user.components.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBalanceUseCase {

    private final UserReader userReader;

    public BalanceResponse execute(Long userId) {
        return BalanceResponse.from(userReader.getUserById(userId));
    }
}
