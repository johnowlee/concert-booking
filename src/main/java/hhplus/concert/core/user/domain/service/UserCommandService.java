package hhplus.concert.core.user.domain.service;

import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.port.UserCommandPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandPort userCommandPort;

    public User save(User user) {
        return userCommandPort.save(user);
    }
}
