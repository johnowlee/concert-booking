package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.controller.request.BalanceChargeRequest;
import hhplus.concert.api.common.UseCase;
import hhplus.concert.api.common.response.UserResponse;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.support.BalanceService;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@UseCase
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final ClockManager clockManager;
    private final BalanceService balanceService;

    public UserResponse execute(Long userId, BalanceChargeRequest request) {
        Balance balance = createChargeBalance(userId, request);

        balanceService.chargeBalanceWithOptimisticLock(balance);

        return UserResponse.from(balance.getUser());
    }

    private Balance createChargeBalance(Long userId, BalanceChargeRequest request) {
        User user = userReader.getUserById(userId);
        long amount = request.balance();
        return Balance.createChargeBalance(user, amount, clockManager);
    }
}
