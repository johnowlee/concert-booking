package hhplus.concert.core.history.balance.components;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.service.TransactionCommandService;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.transaction.infrastructure.repository.TransactionJpaRepository;
import hhplus.concert.core.payment.domain.model.Payment;
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
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Transactional
class TransactionWriterTest extends IntegrationTestSupport {

    @Autowired
    TransactionCommandService transactionCommandService;

    @Autowired
    TransactionJpaRepository transactionJpaRepository;

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

        Transaction chargeTransaction = Transaction.createChargeBalance(savedUser, amount, clockManager.getNowDateTime());

        // when
        transactionCommandService.save(chargeTransaction);

        // then
        List<Transaction> balanceHistories = transactionJpaRepository.findAll();
        assertThat(balanceHistories).hasSize(1)
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

        long totalPrice = 10000L;

        Booking booking = mock(Booking.class);
        given(booking.getUser()).willReturn(savedUser);
        given(booking.getTotalPrice()).willReturn((int) totalPrice);

        User payer = userJpaRepository.findById(savedUser.getId()).get();
        LocalDateTime paymentDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);

        Payment payment = Payment.of(booking, payer, paymentDateTime);
        Transaction transaction = Transaction.createUseBalanceFrom(payment);

        // when
        transactionCommandService.save(transaction);

        // then
        List<Transaction> balanceHistories = transactionJpaRepository.findAll();
        assertThat(balanceHistories).hasSize(1)
                .extracting("transactionType", "transactionDateTime", "amount")
                .contains(
                        tuple(USE, paymentDateTime, 10000L)
                );
    }

}