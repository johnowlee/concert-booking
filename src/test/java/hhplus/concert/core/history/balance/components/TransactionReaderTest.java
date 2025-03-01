package hhplus.concert.core.history.balance.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.transaction.domain.service.TransactionQueryService;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.application.support.ClockManager;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.core.transaction.domain.model.TransactionType.CHARGE;
import static hhplus.concert.core.transaction.domain.model.TransactionType.USE;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class TransactionReaderTest extends IntegrationTestSupport {

    @Autowired
    TransactionQueryService transactionQueryService;

    @Autowired
    TransactionJpaRepository transactionJpaRepository;

    @MockBean
    ClockManager clockManager;

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

        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        Transaction transaction1 = Transaction.builder()
                .user(savedUser)
                .transactionType(CHARGE)
                .transactionDateTime(transactionDateTime)
                .amount(10000L)
                .build();
        Transaction transaction2 = Transaction.builder()
                .user(savedUser)
                .transactionType(USE)
                .transactionDateTime(transactionDateTime.plusHours(1))
                .amount(5000L)
                .build();
        Transaction transaction3 = Transaction.builder()
                .user(savedUser)
                .transactionType(USE)
                .transactionDateTime(transactionDateTime.plusHours(2))
                .amount(2000L)
                .build();

        transactionJpaRepository.saveAll(List.of(transaction1, transaction2, transaction3));

        // when
        long amountBySearchParamWithCharge = transactionQueryService.getAmountBySearchParam(savedUser.getId(), CHARGE, transactionDateTime, transactionDateTime.plusDays(1));
        long amountBySearchParamWithUse = transactionQueryService.getAmountBySearchParam(savedUser.getId(), USE, transactionDateTime, transactionDateTime.plusDays(1));

        // then
        assertThat(amountBySearchParamWithCharge).isEqualTo(10000L);
        assertThat(amountBySearchParamWithUse).isEqualTo(5000L + 2000L);
    }
}