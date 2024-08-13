package hhplus.concert.domain.user.models;

import hhplus.concert.api.exception.RestApiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hhplus.concert.api.exception.code.BalanceErrorCode.NEGATIVE_NUMBER_AMOUNT;
import static hhplus.concert.api.exception.code.BalanceErrorCode.NOT_ENOUGH_BALANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @DisplayName("충전금액이 유효하면 포인트 충전에 성공한다.")
    @Test
    void chargeBalance() {
        //given
        User user = User.builder().balance(0).build();

        //when
        long chargeAmount = 10000L;
        user.chargeBalance(chargeAmount);

        //then
        assertThat(user.getBalance()).isEqualTo(10000L);
    }

    @DisplayName("충전금액이 음수이면, 포인트 충전에 실패한다.")
    @Test
    void chargeBalanceWithNegativeNumber() {
        //given
        User user = User.builder().balance(0).build();

        //when & then
        long amount = -10000L;
        assertThatThrownBy(() -> user.chargeBalance(amount))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NEGATIVE_NUMBER_AMOUNT.getMessage());
    }

    @DisplayName("잔액이 충분하면, 포인트 사용에 성공한다.")
    @Test
    void useBalance() {
        //given
        User user = User.builder().balance(10000L).build();

        //when
        long useAmount = 5000L;
        user.useBalance(useAmount);

        //then
        assertThat(user.getBalance()).isEqualTo(10000L - 5000L);
    }

    @DisplayName("잔액이 불충분하면, 포인트 사용에 실패한다.")
    @Test
    void useBalanceWithNotEnoughBalance() {
        //given
        User user = User.builder().balance(5000L).build();

        //when & then
        long useAmount = 10000L;

        assertThatThrownBy(() -> user.useBalance(useAmount))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_ENOUGH_BALANCE.getMessage());
    }

    @DisplayName("사용금액이 음수이면, 포인트 사용에 실패한다.")
    @Test
    void useBalanceWithNegativeNumber() {
        //given
        User user = User.builder().balance(10000L).build();

        //when & then
        long useAmount = -5000L;
        assertThatThrownBy(() -> user.useBalance(useAmount))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NEGATIVE_NUMBER_AMOUNT.getMessage());
    }
}