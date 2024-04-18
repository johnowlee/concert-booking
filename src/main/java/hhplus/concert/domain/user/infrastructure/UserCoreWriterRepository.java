package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreWriterRepository implements UserWriterRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
