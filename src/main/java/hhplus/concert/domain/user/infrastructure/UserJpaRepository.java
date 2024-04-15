package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
