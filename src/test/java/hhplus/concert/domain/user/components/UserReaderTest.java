package hhplus.concert.domain.user.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.exception.code.UserErrorCode.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class UserReaderTest extends IntegrationTestSupport {

    @Autowired
    UserReader userReader;

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
        User result = userReader.getUserById(savedUser.getId());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(savedUser.getId());
        assertThat(result.getName()).isEqualTo(savedUser.getName());
    }

    @DisplayName("user를 id로 조회했을때 결과가 없으면 예외가 발생한다.")
    @Test
    void getUserByIdWithNotExistedUser() {
        // given
        User user = User.builder()
                .name("jon")
                .version(1L)
                .build();
        userJpaRepository.save(user);

        // when & then
        assertThatThrownBy(() -> userReader.getUserById(99L))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_USER.getMessage());
    }
}