package hhplus.concert.application.user;

import hhplus.concert.application.user.usecase.GetUserByIdUseCase;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.core.user.domain.service.UserQueryService;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.representer.exception.code.UserErrorCode.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class GetUserByIdUseCaseTestHistory {

    @Mock
    private UserQueryService userQueryService;

    @InjectMocks
    private GetUserByIdUseCase getUserByIdUseCase;

    @DisplayName("유저의 잔액을 조회한다.")
    @Test
    void execute() {
        // given
        Long userId = 1L;
        long expectedBalance = 1000L;
        User user = mock(User.class);

        given(userQueryService.getUserById(userId)).willReturn(user);
        given(user.getBalance()).willReturn(expectedBalance);

        // when
        User result = getUserByIdUseCase.execute(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getBalance()).isEqualTo(1000L);
    }

    @DisplayName("등록되지 않은 유저의 잔액 조회 시 예외가 발생한다.")
    @Test
    void executeWithNotFoundUser() {
        // given
        Long userId = 1L;

        given(userQueryService.getUserById(userId)).willThrow(new RestApiException(NOT_FOUND_USER));

        // when & then
        assertThatThrownBy(() -> getUserByIdUseCase.execute(userId))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_USER.getMessage());
    }
}
