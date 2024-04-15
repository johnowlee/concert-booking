package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.entities.user.UserEntity;
import hhplus.concert.domain.user.repositories.UserWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCoreWriterRepository implements UserWriterRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userJpaRepository.save(userEntity);
    }
}
