package hhplus.concert.core.user.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.infrastructure.adapter.UserCommandAdapter;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserCoreWriterRepositoryTest extends IntegrationTestSupport {

    @Autowired
    UserCommandAdapter userCoreWriterRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("user를 저장한다.")
    @Test
    void save() {
        // given
        User user = User.builder()
                .name("jon")
                .build();

        // when
        User savedUser = userCoreWriterRepository.save(user);

        // then
        Optional<User> result = userJpaRepository.findById(savedUser.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(savedUser.getId());
        assertThat(result.get().getName()).isEqualTo(savedUser.getName());
    }
}