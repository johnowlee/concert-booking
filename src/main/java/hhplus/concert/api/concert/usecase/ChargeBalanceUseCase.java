package hhplus.concert.api.concert.usecase;

import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.components.UserWriter;
import hhplus.concert.domain.user.models.UserEntity;
import lombok.RequiredArgsConstructor;

import static hhplus.concert.api.common.ResponseResult.SUCCESS;

@RequiredArgsConstructor
public class ChargeBalanceUseCase {

    private final UserReader userReader;
    private final UserWriter userWriter;

    public BalanceChargeResponse excute(Long userId, long amount) {
        UserEntity userEntity = userReader.findUserByUserId(userId);
        userEntity.setBalance(userEntity.getBalance() + amount);
        UserEntity modifiedUserEntity = userWriter.save(userEntity);
        return BalanceChargeResponse.from(SUCCESS, modifiedUserEntity.getBalance());
    }
}
