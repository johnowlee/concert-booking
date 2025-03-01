package hhplus.concert.core.user.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.user.domain.service.UserCommandService;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class UserCommandServiceTest extends IntegrationTestSupport {

    @Autowired
    UserCommandService userCommandService;

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
        User savedUser = userCommandService.save(user);

        // then
        Optional<User> result = userJpaRepository.findById(savedUser.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(savedUser.getId());
        assertThat(result.get().getName()).isEqualTo(savedUser.getName());
    }

}