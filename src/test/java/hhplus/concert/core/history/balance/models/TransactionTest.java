package hhplus.concert.core.history.balance.models;

import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.booking.domain.model.Booking;
import hhplus.concert.core.payment.domain.model.Payment;
import hhplus.concert.application.support.ClockManager;
import hhplus.concert.core.user.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static hhplus.concert.core.transaction.domain.model.TransactionType.CHARGE;
import static hhplus.concert.core.transaction.domain.model.TransactionType.USE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TransactionTest {
    @Mock
    private ClockManager clockManager;

    @InjectMocks
    private Transaction transaction;

    @DisplayName("Transaction Type이 Charge인 Balance를 생성한다.")
    @Test
    void createChargeBalance() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        long amount = 10000L;
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        given(clockManager.getNowDateTime()).willReturn(transactionDateTime);

        // when
        Transaction chargeTransaction = Transaction.createChargeBalance(user, amount, clockManager.getNowDateTime());

        // then
        assertThat(chargeTransaction.getTransactionType()).isEqualTo(CHARGE);
        assertThat(chargeTransaction.getAmount()).isEqualTo(10000L);
        assertThat(chargeTransaction.getTransactionDateTime()).isEqualTo(transactionDateTime);
        assertThat(chargeTransaction.getUser()).isEqualTo(user);
    }

    @DisplayName("Payment 파라미터로 Transaction Type이 Use인 Balance를 생성한다.")
    @Test
    void createUseBalanceFrom() {
        // given
        User user = User.builder()
                .name("jon")
                .build();
        long totalPrice = 10000L;
        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        Booking booking = mock(Booking.class);
        given(booking.getUser()).willReturn(user);
        given(booking.getTotalPrice()).willReturn((int) totalPrice);

        Payment payment = Payment.of(booking, booking.getUser(), transactionDateTime);

        // when
        Transaction useTransaction = Transaction.createUseBalanceFrom(payment);

        /// then
        assertThat(useTransaction.getTransactionType()).isEqualTo(USE);
        assertThat(useTransaction.getAmount()).isEqualTo(10000L);
        assertThat(useTransaction.getTransactionDateTime()).isEqualTo(transactionDateTime);
        assertThat(useTransaction.getUser()).isEqualTo(user);
    }

}