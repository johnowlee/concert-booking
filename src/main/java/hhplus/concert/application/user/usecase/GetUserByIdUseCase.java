package hhplus.concert.application.user.usecase;

import hhplus.concert.core.user.domain.service.UserQueryService;
import hhplus.concert.core.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UserQueryService userQueryService;

    public User execute(Long userId) {
        return userQueryService.getUserById(userId);
    }
}
