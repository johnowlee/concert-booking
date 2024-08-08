package hhplus.concert.domain.history.balance.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.history.balance.models.TransactionType.CHARGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BalanceCoreWriterRepositoryTest extends IntegrationTestSupport {

    @Autowired
    BalanceCoreWriterRepository balanceCoreWriterRepository;

    @Autowired
    BalanceJpaRepository balanceJpaRepository;

    @DisplayName("balance를 저장한다.")
    @Test
    void save() {
        // given
        User user = User.builder()
                .id(1L)
                .name("jon")
                .version(1L)
                .build();
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 8, 00, 25);
        Balance balance = Balance.builder()
                .user(user)
                .transactionType(CHARGE)
                .transactionDateTime(transactionDateTime)
                .amount(10000L)
                .build();

        // when
        Balance savedBalance = balanceCoreWriterRepository.save(balance);

        // then
        List<Balance> balances = balanceJpaRepository.findAll();
        assertThat(balances).hasSize(1)
                .extracting("transactionType", "transactionDateTime", "amount")
                .contains(
                        tuple(CHARGE, transactionDateTime, 10000L)
                );
    }

}