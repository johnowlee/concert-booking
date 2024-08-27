package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.controller.request.BalanceChargeRequest;
import hhplus.concert.api.common.response.UserResponse;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.history.balance.components.BalanceWriter;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final BalanceWriter balanceWriter;
    private final EntityManager em;
    private final ClockManager clockManager;

    public UserResponse execute(Long userId, BalanceChargeRequest request) {
        try {
            long amount = request.balance();
            User user = userReader.getUserById(userId);

            chargeBalance(user, amount);

            balanceWriter.saveChargeBalance(user, amount, clockManager);

            return UserResponse.forBalanceResponseFrom(user);
        } catch (OptimisticLockException e) {
            throw new RestApiException(BalanceErrorCode.FAILED_CHARGE);
        }
    }

    private void chargeBalance(User user, long amount) {
        user.chargeBalance(amount);
        em.flush();
    }
}
