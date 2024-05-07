package hhplus.concert.domain.user.models;

import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @DisplayName("인자값이 모두 유효하면, 포인트 충전에 성공한다.")
    @Test
    void chargeBalance_Success_ifWithValidArgument() {
        //given
        User user = User.builder().id(1L).balance(0).build();

        long amount = 10000L;

        //when
        user.chargeBalance(amount);

        //then
        assertThat(user.getBalance()).isEqualTo(10000L);
    }

    @DisplayName("충전금액에 음수이면, 포인트 충전에 실패한다.")
    @Test
    void chargeBalance_Failure_ifWithNegativeAmount() {
        //given
        User user = User.builder().id(1L).balance(0).build();

        long amount = -10000L;

        //when & then
        assertThatThrownBy(() ->user.chargeBalance(amount))
                .isInstanceOf(
                        new RestApiException(BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT).getClass()
                );

    }

    @DisplayName("유저의 잔액이 충분하면, 포인트 사용에 성공한다.")
    @Test
    void useBalance_Success_ifWithEnoughBalance() {
        //given
        User user = User.builder().id(1L).balance(10000L).build();

        long useAmount = 5000L;

        //when
        user.useBalance(useAmount);

        //then
        assertThat(user.getBalance()).isEqualTo(10000L - 5000L);
    }

    @DisplayName("사용금액이 음수이면, 포인트 사용에 실패한다.")
    @Test
    void useBalance_Failure_ifWithNegativeAmount() {
        //given
        User user = User.builder().id(1L).balance(10000L).build();

        long amount = -5000L;

        //when & then
        assertThatThrownBy(() ->user.useBalance(amount))
                .isInstanceOf(
                        new RestApiException(BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT).getClass()
                );

    }

    @DisplayName("보유금액이 불충분하면, 포인트 사용에 실패한다.")
    @Test
    void useBalance_Failure_ifWithNotEnoughBalance() {
        //given
        User user = User.builder().id(1L).balance(5000L).build();

        long amount = 10000L;

        //when & then
        assertThatThrownBy(() ->user.useBalance(amount))
                .isInstanceOf(
                        new RestApiException(BalanceErrorCode.NOT_ENOUGH_BALANCE).getClass()
                );

    }
}