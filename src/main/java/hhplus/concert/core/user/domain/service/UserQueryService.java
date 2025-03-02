package hhplus.concert.core.user.domain.service;

import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.port.UserQueryPort;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryPort userQueryPort;

    public User getUserById(Long userId) {
        return userQueryPort.findUserById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.NOT_FOUND_USER));
    }
}
