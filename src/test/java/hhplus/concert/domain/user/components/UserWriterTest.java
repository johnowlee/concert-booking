package hhplus.concert.domain.user.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserWriterTest extends IntegrationTestSupport {

    @Autowired
    UserWriter userWriter;

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
        User savedUser = userWriter.save(user);

        // then
        Optional<User> result = userJpaRepository.findById(savedUser.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(savedUser.getId());
        assertThat(result.get().getName()).isEqualTo(savedUser.getName());
    }

}