package hhplus.concert.domain.user.repositories;

import hhplus.concert.domain.user.models.User;

import java.util.Optional;

public interface UserReaderRepository {

    Optional<User> getUserById(Long userId);

}
