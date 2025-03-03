package hhplus.concert.representer.api.user;

import hhplus.concert.application.user.usecase.*;
import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.representer.api.RestApiResponse;
import hhplus.concert.representer.api.user.request.BalanceChargeRequest;
import hhplus.concert.representer.api.user.request.CreateUserRequest;
import hhplus.concert.representer.api.user.response.BalanceResponse;
import hhplus.concert.representer.api.user.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// TODO: 2025-02-27 test
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final ChargeBalanceUseCase chargeBalanceUseCase;
    private final UserControllerMapper mapper;

    @PostMapping()
    public RestApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = createUserUseCase.execute(createUserRequest.name());
        return RestApiResponse.ok(mapper.toUserResponse(user));
    }

    @GetMapping("/{userId}")
    public RestApiResponse<UserResponse> fetchUser(@PathVariable Long userId) {
        User user = getUserByIdUseCase.execute(userId);
        return RestApiResponse.ok(mapper.toUserResponse(user));
    }

    @GetMapping("/{userId}/balance")
    public RestApiResponse<BalanceResponse> fetchBalance(@PathVariable Long userId) {
        User user = getUserByIdUseCase.execute(userId);
        return RestApiResponse.ok(mapper.toBalanceResponse(user));
    }

    @PatchMapping("/{userId}/balance")
    public RestApiResponse<BalanceResponse> chargeBalance(@PathVariable Long userId,
                                                          @Valid @RequestBody BalanceChargeRequest request) {
        User user = chargeBalanceUseCase.execute(userId, mapper.toBalanceBalanceChargeRequest(request));
        return RestApiResponse.ok(mapper.toBalanceResponse(user));
    }
}
