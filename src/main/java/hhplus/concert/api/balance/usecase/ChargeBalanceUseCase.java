package hhplus.concert.api.balance.usecase;

import hhplus.concert.api.balance.dto.response.BalanceChargeResponse;
import hhplus.concert.domain.user.components.UserReader;
import hhplus.concert.domain.user.components.UserWriter;
import hhplus.concert.entities.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static hhplus.concert.api.common.ResponseResult.SUCCESS;

@Service
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
