package hhplus.concert.core.user.infrastructure.repository;

import hhplus.concert.core.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
