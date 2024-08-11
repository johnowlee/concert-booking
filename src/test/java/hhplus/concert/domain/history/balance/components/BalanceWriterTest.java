package hhplus.concert.domain.history.balance.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.history.balance.infrastructure.BalanceJpaRepository;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.history.balance.models.TransactionType.CHARGE;
import static hhplus.concert.domain.history.balance.models.TransactionType.USE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;

@Transactional
class BalanceWriterTest extends IntegrationTestSupport {

    @Autowired
    BalanceWriter balanceWriter;

    @Autowired
    BalanceJpaRepository balanceJpaRepository;

    @MockBean
    ClockManager clockManager;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("Balance 충전 내역을 저장한다.")
    @Test
    void saveChargeBalance() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        long amount = 10000L;
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        given(clockManager.getNowDateTime()).willReturn(transactionDateTime);

        // when
        balanceWriter.saveChargeBalance(savedUser, amount, clockManager);

        // then
        List<Balance> balances = balanceJpaRepository.findAll();
        assertThat(balances).hasSize(1)
                .extracting("transactionType", "transactionDateTime", "amount")
                .contains(
                        tuple(CHARGE, transactionDateTime, 10000L)
                );
    }

    @DisplayName("Balance 사용 내역을 저장한다.")
    @Test
    void saveUseBalance() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        User savedUser = userJpaRepository.save(user);

        long amount = 10000L;
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        given(clockManager.getNowDateTime()).willReturn(transactionDateTime);

        // when
        balanceWriter.saveUseBalance(savedUser, amount, clockManager);

        // then
        List<Balance> balances = balanceJpaRepository.findAll();
        assertThat(balances).hasSize(1)
                .extracting("transactionType", "transactionDateTime", "amount")
                .contains(
                        tuple(USE, transactionDateTime, 10000L)
                );
    }

}