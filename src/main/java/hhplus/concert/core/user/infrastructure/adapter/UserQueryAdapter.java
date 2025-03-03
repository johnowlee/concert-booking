package hhplus.concert.core.user.infrastructure.adapter;

import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.port.UserQueryPort;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserQueryAdapter implements UserQueryPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findUserById(Long userId) {
        return userJpaRepository.findById(userId);
    }

}
