package hhplus.concert.application.user;

import hhplus.concert.application.dto.BalanceChargeDto;
import hhplus.concert.domain.history.balance.models.Balance;
import hhplus.concert.domain.history.balance.service.BalanceService;
import hhplus.concert.domain.support.ClockManager;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final ClockManager clockManager;
    private final BalanceService balanceService;

    public User execute(Long userId, BalanceChargeDto dto) {
        Balance balance = createChargeBalance(userId, dto);

        balanceService.chargeBalanceWithOptimisticLock(balance);

        return balance.getUser();
    }

    private Balance createChargeBalance(Long userId, BalanceChargeDto dto) {
        User user = userReader.getUserById(userId);
        long amount = dto.balance();
        return Balance.createChargeBalance(user, amount, clockManager);
    }
}
