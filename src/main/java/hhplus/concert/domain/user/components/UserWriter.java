package hhplus.concert.domain.user.components;

import hhplus.concert.entities.user.UserEntity;
import hhplus.concert.domain.user.repositories.UserWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWriter {

    private final UserWriterRepository userWriterRepository;

    public UserEntity save(UserEntity userEntity) {
        return userWriterRepository.save(userEntity);
    }

}
