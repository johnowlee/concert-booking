package hhplus.concert.domain.user.components;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserReaderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserReaderTest {

    @InjectMocks
    UserReader userReader;

    @Mock
    UserReaderRepository userReaderRepository;

    @DisplayName("인자값이 모두 유효하면, 유저 조회에 성공한다.")
    @Test
    void getUserById_Success_ifWithValidArguments() {
        // given
        Long userId = 1L;
        String name = "John Doe";
        User expected = User.builder()
                .id(userId)
                .name(name)
                .build();

        given(userReaderRepository.getUserById(userId)).willReturn(Optional.ofNullable(expected));

        // when
        User result = userReader.getUserById(userId);

        // then
        assertThat(result.getId()).isEqualTo(expected.getId());
        assertThat(result.getName()).isEqualTo(expected.getName());
        verify(userReaderRepository, times(1)).getUserById(userId);
    }
}