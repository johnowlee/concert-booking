package hhplus.concert.domain.user.components;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserWriterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserWriterTest {

    @InjectMocks
    UserWriter userWriter;

    @Mock
    UserWriterRepository userWriterRepository;

    @DisplayName("인자값이 모두 유효하면, 유저 저장에 성공한다.")
    @Test
    void save_Success_ifWithValidArguments() {
        // given
        Long userId = 1L;
        String name = "John Doe";
        User expected = User.builder()
                .id(userId)
                .name(name)
                .build();

        given(userWriterRepository.save(any(User.class))).willReturn(expected);

        // when
        User user = User.builder()
                .id(userId)
                .name(name)
                .build();
        User result = userWriter.save(user);

        // then
        assertThat(result.getId()).isEqualTo(expected.getId());
        assertThat(result.getName()).isEqualTo(expected.getName());
        verify(userWriterRepository, times(1)).save(any(User.class));
    }

}