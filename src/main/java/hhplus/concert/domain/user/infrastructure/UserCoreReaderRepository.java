package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreReaderRepository implements UserReaderRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getUserById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

}
