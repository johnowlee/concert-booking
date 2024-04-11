package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.domain.user.models.UserEntity;
import hhplus.concert.domain.user.repositories.UserReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCoreReaderRepository implements UserReaderRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserEntity> findByUserId(Long userId) {
        return userJpaRepository.findById(userId);
    }

}
