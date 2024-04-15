package hhplus.concert.domain.user.components;

import hhplus.concert.domain.user.models.User;
import hhplus.concert.domain.user.repositories.UserWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class UserWriter {

    private final UserWriterRepository userWriterRepository;

    public User save(User user) {
        return userWriterRepository.save(user);
    }

    public User update(User user) {
        return userWriterRepository.update(user);
    }
}
