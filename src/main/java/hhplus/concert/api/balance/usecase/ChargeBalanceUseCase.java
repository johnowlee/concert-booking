package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.domain.balance.components.BalanceHistoryWriter;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.concert.api.common.ResponseResult.SUCCESS;
import static hhplus.concert.domain.balance.models.TransactionType.CHARGE;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final BalanceHistoryWriter balanceHistoryWriter;

    public BalanceChargeResponse excute(Long userId, long amount) {
        User user = userReader.getUserById(userId);
        user.chargeBalance(amount);

        balanceHistoryWriter.saveBalanceUseHistory(user, amount, CHARGE);
        return BalanceChargeResponse.from(SUCCESS, user.getBalance());
    }
}
