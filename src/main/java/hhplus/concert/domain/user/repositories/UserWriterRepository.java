package hhplus.concert.domain.user.repositories;

import hhplus.concert.domain.user.models.UserEntity;

public interface UserWriterRepository {

    UserEntity save(UserEntity userEntity);
}
