package hhplus.concert.domain.history.balance.models;

import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static hhplus.concert.domain.history.balance.models.TransactionType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BalanceTest {
    @Mock
    private ClockManager clockManager;

    @InjectMocks
    private Balance balance;

    @DisplayName("Transaction Type이 Charge인 Balance를 생성한다.")
    @Test
    void createChargeBalance() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        long amount = 10000L;
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        given(clockManager.getDateTime()).willReturn(transactionDateTime);

        // when
        Balance chargeBalance = Balance.createChargeBalance(user, amount, clockManager);

        // then
        assertThat(chargeBalance.getTransactionType()).isEqualTo(CHARGE);
        assertThat(chargeBalance.getAmount()).isEqualTo(10000L);
        assertThat(chargeBalance.getTransactionDateTime()).isEqualTo(transactionDateTime);
        assertThat(chargeBalance.getUser()).isEqualTo(user);
    }

    @DisplayName("Transaction Type이 Use인 Balance를 생성한다.")
    @Test
    void createUseBalance() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        long amount = 10000L;
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        given(clockManager.getDateTime()).willReturn(transactionDateTime);

        // when
        Balance chargeBalance = Balance.createUseBalance(user, amount, clockManager);

        /// then
        assertThat(chargeBalance.getTransactionType()).isEqualTo(USE);
        assertThat(chargeBalance.getAmount()).isEqualTo(10000L);
        assertThat(chargeBalance.getTransactionDateTime()).isEqualTo(transactionDateTime);
        assertThat(chargeBalance.getUser()).isEqualTo(user);
    }

}