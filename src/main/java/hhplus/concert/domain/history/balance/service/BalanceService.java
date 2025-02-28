package hhplus.concert.domain.history.balance.service;

import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.BalanceErrorCode;
import hhplus.concert.domain.history.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.history.balance.models.Balance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BalanceService {

    private final BalanceHistoryWriter balanceHistoryWriter;
    private final EntityManager em;

    public Balance chargeBalanceWithOptimisticLock(Balance balance) {
        try {
            chargeBalance(balance);

            balanceHistoryWriter.save(balance);
            return balance;
        } catch (OptimisticLockException e) {
            throw new RestApiException(BalanceErrorCode.FAILED_CHARGE);
        }
    }

    private void chargeBalance(Balance balance) {
        long chargeAmount = balance.getAmount();
        balance.getUser().chargeBalance(chargeAmount);
        em.flush();
    }
}
