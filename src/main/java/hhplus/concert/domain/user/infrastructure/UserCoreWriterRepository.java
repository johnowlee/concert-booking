package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserWriterRepository;
import hhplus.concert.entities.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class UserCoreWriterRepository implements UserWriterRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .balance(user.getBalance())
                .build();
        return userJpaRepository.save(userEntity).toUser();
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = userJpaRepository.findById(user.getId())
                .orElseThrow(NoSuchElementException::new);
        userEntity.updateUserEntity(user);
        return userEntity.toUser();
    }
}
