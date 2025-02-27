package hhplus.concert.application.user;

import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetBalanceUseCase {

    private final UserReader userReader;

    public User execute(Long userId) {
        return userReader.getUserById(userId);
    }
}
