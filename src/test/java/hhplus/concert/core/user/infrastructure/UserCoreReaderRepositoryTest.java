package hhplus.concert.core.user.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.infrastructure.adapter.UserQueryAdapter;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserCoreReaderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    UserQueryAdapter userCoreReaderRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("user를 id로 조회한다.")
    @Test
    void getUserById() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        // when
        Optional<User> result = userJpaRepository.findById(savedUser.getId());

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(savedUser.getId());
        assertThat(result.get().getName()).isEqualTo(savedUser.getName());
    }

    @DisplayName("user를 id로 조회했을때 결과가 없으면 null을 반환한다.")
    @Test
    void getUserByIdWithNotExistedUser() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        // when
        Optional<User> result = userJpaRepository.findById(2L);

        // then
        assertThat(result.isEmpty()).isTrue();
    }
}