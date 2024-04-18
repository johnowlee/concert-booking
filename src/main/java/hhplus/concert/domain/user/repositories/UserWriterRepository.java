package hhplus.concert.domain.user.repositories;

import hhplus.concert.domain.user.models.User;

public interface UserWriterRepository {

    User save(User user);
}
