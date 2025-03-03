package hhplus.concert.core.user.domain.port;

import hhplus.concert.core.user.domain.model.User;

public interface UserCommandPort {

    User save(User user);
}
