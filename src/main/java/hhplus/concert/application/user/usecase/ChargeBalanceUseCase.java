package hhplus.concert.application.user.usecase;

import hhplus.concert.application.user.data.command.BalanceChargeCommand;
import hhplus.concert.core.transaction.domain.model.Transaction;
import hhplus.concert.core.transaction.domain.service.TransactionCommandService;
import hhplus.concert.application.support.ClockManager;
import hhplus.concert.core.user.domain.service.UserQueryService;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.representer.exception.RestApiException;
import hhplus.concert.representer.exception.code.BalanceErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChargeBalanceUseCase {

    private final UserQueryService userQueryService;
    private final TransactionCommandService transactionCommandService;
    private final ClockManager clockManager;
    private final EntityManager em;

    public User execute(Long userId, BalanceChargeCommand dto) {
        User user = userQueryService.getUserById(userId);
        long amount = dto.balance();
        Transaction transaction = Transaction.createChargeBalance(user, amount, clockManager.getNowDateTime());
        chargeBalanceWithOptimisticLock(transaction);
        return transaction.getUser();
    }

    private void chargeBalanceWithOptimisticLock(Transaction transaction) {
        try {
            long chargeAmount = transaction.getAmount();
            transaction.getUser().chargeBalance(chargeAmount);
            em.flush();
            transactionCommandService.save(transaction);
        } catch (OptimisticLockException e) {
            throw new RestApiException(BalanceErrorCode.FAILED_CHARGE);
        }
    }
}
