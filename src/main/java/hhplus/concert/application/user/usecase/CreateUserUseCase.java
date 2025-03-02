package hhplus.concert.application.user.usecase;

import hhplus.concert.core.user.domain.model.User;
import hhplus.concert.core.user.domain.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserCommandService userCommandService;

    public User execute(String name) {
        return userCommandService.save(User.createUser(name));
    }
}
