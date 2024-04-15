package hhplus.concert.domain.user.repositories;

import hhplus.concert.entities.user.UserEntity;

import java.util.Optional;

public interface UserReaderRepository {

    Optional<UserEntity> findByUserId(Long userId);

}
