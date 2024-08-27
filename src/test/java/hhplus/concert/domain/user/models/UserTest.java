package hhplus.concert.domain.user.models;

import hhplus.concert.api.exception.RestApiException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hhplus.concert.api.exception.code.BalanceErrorCode.NOT_ENOUGH_BALANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @DisplayName("잔액 충전에 성공한다.")
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

    @DisplayName("잔액이 비교액 미만이면 true를 반환한다.")
    @Test
    void isBalanceLessThan() {
        // given
        long targetAmount = 20000L;
        User user = User.builder()
                .balance(10000L)
                .build();

        // when
        boolean result = user.isBalanceLessThan(targetAmount);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("잔액이 비교액 이상이면 false를 반환한다.")
    @Test
    void isBalanceNotLessThan() {
        // given
        long equalAmount = 10000L;
        long greaterAmount = 5000L;
        User user = User.builder()
                .balance(10000L)
                .build();

        // when
        boolean equalAmountResult = user.isBalanceLessThan(equalAmount);
        boolean greaterAmountResult = user.isBalanceLessThan(greaterAmount);

        // then
        Assertions.assertThat(equalAmountResult).isFalse();
        Assertions.assertThat(greaterAmountResult).isFalse();
    }
}