package hhplus.concert.core.user.infrastructure.adapter;

import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.port.UserCommandPort;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandAdapter implements UserCommandPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
