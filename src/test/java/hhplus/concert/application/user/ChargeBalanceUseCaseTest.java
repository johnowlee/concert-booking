package hhplus.concert.application.user;

import hhplus.concert.IntegrationTestSupport;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.application.user.dto.BalanceChargeDto;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.components.UserWriter;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static hhplus.concert.representer.exception.code.UserErrorCode.NOT_FOUND_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChargeBalanceUseCaseTest extends IntegrationTestSupport {

    @Autowired
    ChargeBalanceUseCase chargeBalanceUseCase;

    @Autowired
    UserReader userReader;

    @Autowired
    UserWriter userWriter;

    @Autowired
    BalanceHistoryWriter balanceHistoryWriter;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("유저의 잔액을 충전한다.")
    @Transactional
    @Test
    void execute() {
        // given
        User savingUser = User.builder()
                .name("jon")
                .balance(10000L)
                .build();
        User savedUser = userWriter.save(savingUser);

        User user = userReader.getUserById(savedUser.getId());
        long balance = user.getBalance();
        long version = user.getVersion();

        // when
        User result = chargeBalanceUseCase.execute(user.getId(), new BalanceChargeDto(30000L));

        // then
        assertThat(user.getVersion()).isEqualTo(version + 1);
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getName()).isEqualTo("jon");
        assertThat(result.getBalance()).isEqualTo(balance + 30000L);
    }

    @DisplayName("등록되지 않은 유저의 잔액 충전 시 예외가 발생한다.")
    @Test
    void executeWithNotFoundUser() {
        // given
        User savingUser = User.builder()
                .name("jon")
                .balance(10000L)
                .build();
        User savedUser = userWriter.save(savingUser);

        User user = userReader.getUserById(savedUser.getId());
        Long userId = user.getId();
        Long notFoundUserId = 20L;

        // when & then
        assertThat(userId).isNotEqualTo(notFoundUserId);
        assertThatThrownBy(() -> chargeBalanceUseCase.execute(notFoundUserId, new BalanceChargeDto(30000L)))
                .isInstanceOf(RestApiException.class)
                .hasMessage(NOT_FOUND_USER.getMessage());
    }

    @DisplayName("동시에 잔액충전이 발생하면 낙관적락에 의해 1건만 수행된다.")
    @Test
    void executeConcurrently() throws InterruptedException {
        // given
        User savingUser = User.builder()
                .name("jon")
                .balance(10000L)
                .build();
        User savedUser = userWriter.save(savingUser);

        User user = userReader.getUserById(savedUser.getId());
        Long userId = user.getId();

        int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    chargeBalanceUseCase.execute(userId, new BalanceChargeDto(30000L));
                } catch (RestApiException e) {
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();  // 모든 스레드가 작업을 마칠 때까지 대기
        executorService.shutdown(); // 스레드 풀이 더 이상 작업을 기다리지 않도록 종료

        // then
        User result = userReader.getUserById(userId);
        assertThat(result.getBalance()).isEqualTo(10000L + 30000L);
    }
}