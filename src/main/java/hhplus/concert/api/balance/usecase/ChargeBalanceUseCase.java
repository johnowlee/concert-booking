package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.api.exception.RestApiException;
import hhplus.concert.api.exception.code.BalanceErrorCode;
import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.common.ResponseResult.SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final BalanceHistoryWriter balanceHistoryWriter;
    private final EntityManager em;

    public BalanceChargeResponse execute(Long userId, long amount) {
        try {
            User user = userReader.getUserById(userId);
            user.chargeBalance(amount);
            em.flush();
            balanceHistoryWriter.saveBalanceChargeHistory(user, amount);
            return BalanceChargeResponse.from(SUCCESS, user.getBalance());
        } catch (OptimisticLockException e) {
            throw new RestApiException(BalanceErrorCode.FAILED_CHARGE);
        }
    }
}
