package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.dto.response.BalanceResponse;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static hhplus.concert.api.exception.code.UserErrorCode.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class GetBalanceUseCaseTest {

    @Mock
    private UserReader userReader;

    @InjectMocks
    private GetBalanceUseCase getBalanceUseCase;

    @DisplayName("유저의 잔액을 조회한다.")
    @Test
    void execute() {
        // given
        Long userId = 1L;
        long expectedBalance = 1000L;
        User user = mock(User.class);

        given(userReader.getUserById(userId)).willReturn(user);
        given(user.getBalance()).willReturn(expectedBalance);

        // when
        BalanceResponse result = getBalanceUseCase.execute(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.balance()).isEqualTo(1000L);
    }

    @DisplayName("등록되지 않은 유저의 잔액 조회 시 예외가 발생한다.")
    @Test
    void executeWithNotFoundUser() {
        // given
        Long userId = 1L;

        given(userReader.getUserById(userId)).willThrow(new RestApiException(NOT_FOUND_USER));

        // when & then
        assertThatThrownBy(() -> getBalanceUseCase.execute(userId))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_USER.getMessage());
    }
}
