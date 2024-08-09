package hhplus.concert.domain.history.balance.service;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.domain.booking.models.Booking;
import hhplus.concert.domain.history.balance.components.BalanceWriter;
import hhplus.concert.domain.history.balance.infrastructure.BalanceJpaRepository;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.payment.event.PaymentCompleteEvent;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.support.event.EventPublisher;
import hhplus.concert.domain.user.infrastructure.UserJpaRepository;
import hhplus.concert.domain.user.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static hhplus.concert.domain.history.balance.models.TransactionType.USE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@Transactional
class BalanceServiceTest extends IntegrationTestSupport {

    @Autowired
    BalanceService balanceService;

    @Autowired
    BalanceWriter balanceWriter;

    @Autowired
    BalanceJpaRepository balanceJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @MockBean
    ClockManager clockManager;

    @MockBean
    EventPublisher eventPublisher;

    @DisplayName("유저의 계좌에 잔액이 차감되고 이벤트발행 및 balance 거래 내역을 저장한다.")
    @Test
    void use() {
        // given
        User user = User.builder()
                .name("jon")
                .balance(20000)
                .build();
        User savedUser = userJpaRepository.save(user);
        Booking booking = mock(Booking.class);

        given(booking.getUser()).willReturn(savedUser);
        given(booking.getTotalPrice()).willReturn(10000);

        LocalDateTime transactionDateTime = LocalDateTime.of(2024, 8, 9, 11, 30, 30);
        given(clockManager.getDateTime()).willReturn(transactionDateTime);

        // when
        balanceService.use(booking);

        // then
        assertThat(user.getBalance()).isEqualTo(20000 - 10000);
        then(eventPublisher).should(times(1)).publish(any(PaymentCompleteEvent.class));

        List<Balance> balances = balanceJpaRepository.findAll();
        assertThat(balances).hasSize(1)
                .extracting("user", "transactionDateTime", "TransactionType", "amount")
                .contains(
                        tuple(savedUser, transactionDateTime, USE, 10000L)
                );
    }
}