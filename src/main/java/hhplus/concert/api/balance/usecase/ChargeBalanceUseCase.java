package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.controller.request.BalanceChargeRequest;
import hhplus.concert.api.common.response.UserResponse;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.history.balance.components.BalanceWriter;
import hhplus.concert.domain.history.balance.models.Balance;
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
            Balance balance = createChargeBalance(userId, request);

            chargeBalance(balance);

            balanceWriter.saveBalance(balance);
            return UserResponse.from(balance.getUser());
        } catch (OptimisticLockException e) {
            throw new RestApiException(BalanceErrorCode.FAILED_CHARGE);
        }
    }

    private Balance createChargeBalance(Long userId, BalanceChargeRequest request) {
        User user = userReader.getUserById(userId);
        long amount = request.balance();
        return Balance.createChargeBalance(user, amount, clockManager);
    }

    private void chargeBalance(Balance balance) {
        long chargeAmount = balance.getAmount();
        balance.getUser().chargeBalance(chargeAmount);
        em.flush();
    }
}
