package hhplus.concert.domain.user.infrastructure;

import hhplus.concert.domain.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
