package hhplus.concert.core.history.balance.infrastructure;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.infrastructure.adapter.TransactionCommandAdapter;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import hhplus.concert.core.user.infrastructure.repository.UserJpaRepository;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.core.transaction.domain.model.TransactionType.CHARGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class TransactionCoreWriterRepositoryTest extends IntegrationTestSupport {

    @Autowired
    TransactionCommandAdapter balanceCoreWriterRepository;

    @Autowired
    TransactionJpaRepository transactionJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("balance를 저장한다.")
    @Test
    void save() {
        // given
        User user = User.builder()
                .name("jon")
                .version(1L)
                .build();
        User savedUser = userJpaRepository.save(user);

        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 8, 00, 25);
        Transaction transaction = Transaction.builder()
                .user(savedUser)
                .transactionType(CHARGE)
                .transactionDateTime(transactionDateTime)
                .amount(10000L)
                .build();

        // when
        balanceCoreWriterRepository.save(transaction);

        // then
        List<Transaction> balanceHistories = transactionJpaRepository.findAll();
        assertThat(balanceHistories).hasSize(1)
                .extracting("transactionType", "transactionDateTime", "amount")
                .contains(
                        tuple(CHARGE, transactionDateTime, 10000L)
                );
    }

}