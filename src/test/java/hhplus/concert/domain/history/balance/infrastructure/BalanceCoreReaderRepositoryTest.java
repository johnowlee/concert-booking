package hhplus.concert.domain.history.balance.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.history.balance.models.TransactionType.CHARGE;
import static hhplus.concert.domain.history.balance.models.TransactionType.USE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BalanceCoreReaderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    BalanceCoreReaderRepository balanceCoreReaderRepository;

    @Autowired
    BalanceJpaRepository balanceJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("transaction type에 따라 balance 거래 내역 합계액이 조회된다.")
    @Test
    void getAmountBySearchParam() {
        // given
        User user = User.builder()
                .name("jon")
                .version(1L)
                .build();
        User savedUser = userJpaRepository.save(user);

        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 8, 00, 25);
        Balance balance1 = Balance.builder()
                .user(savedUser)
                .transactionType(CHARGE)
                .transactionDateTime(transactionDateTime)
                .amount(10000L)
                .build();
        Balance balance2 = Balance.builder()
                .user(savedUser)
                .transactionType(USE)
                .transactionDateTime(transactionDateTime.plusHours(1))
                .amount(5000L)
                .build();
        Balance balance3 = Balance.builder()
                .user(savedUser)
                .transactionType(USE)
                .transactionDateTime(transactionDateTime.plusHours(2))
                .amount(2000L)
                .build();

        balanceJpaRepository.saveAll(List.of(balance1, balance2, balance3));

        // when
        long amountBySearchParamWithCharge = balanceCoreReaderRepository.getAmountBySearchParam(savedUser.getId(), CHARGE, transactionDateTime, transactionDateTime.plusDays(1));
        long amountBySearchParamWithUse = balanceCoreReaderRepository.getAmountBySearchParam(savedUser.getId(), USE, transactionDateTime, transactionDateTime.plusDays(1));

        // then
        assertThat(amountBySearchParamWithCharge).isEqualTo(10000L);
        assertThat(amountBySearchParamWithUse).isEqualTo(5000L + 2000L);
    }

}