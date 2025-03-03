package hhplus.concert.representer.api.user;

import hhplus.concert.application.user.data.command.BalanceChargeCommand;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.representer.api.user.request.BalanceChargeRequest;
import hhplus.concert.representer.api.user.response.BalanceResponse;
import hhplus.concert.representer.api.user.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserControllerMapper {

    public BalanceResponse toBalanceResponse(User user) {
        return new BalanceResponse(user.getBalance());
    }

    public BalanceChargeCommand toBalanceBalanceChargeRequest(BalanceChargeRequest request) {
        return new BalanceChargeCommand(request.balance());
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getBalance());
    }
}
