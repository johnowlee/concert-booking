package hhplus.concert.domain.user.components;

import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.UserErrorCode;
import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserReaderRepository userReaderRepository;

    public User getUserById(Long userId) {
        return userReaderRepository.getUserById(userId)
                .orElseThrow(() -> new RestApiException(UserErrorCode.NOT_FOUND_USER));
    }
}
