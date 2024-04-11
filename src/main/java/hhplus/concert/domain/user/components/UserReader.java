package hhplus.concert.domain.user.components;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserReaderRepository userReaderRepository;

    public User findUserByUserId(Long userId) {
        return userReaderRepository.findByUserId(userId)
                .orElseThrow(NoSuchElementException::new);
    }
}
