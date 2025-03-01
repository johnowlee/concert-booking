package hhplus.concert.core.user.domain.port;

import hhplus.concert.core.user.domain.model.User;

import java.util.Optional;

public interface UserQueryPort {

    Optional<User> getUserById(Long userId);

}
