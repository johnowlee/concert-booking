package hhplus.concert.domain.user.repositories;

import hhplus.concert.entities.user.UserEntity;

public interface UserWriterRepository {

    UserEntity save(UserEntity userEntity);
}
