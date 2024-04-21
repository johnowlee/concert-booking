package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class QueueCoreReaderRepository implements QueueReaderRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue getQueueByUserId(Long userId) {
        return queueJpaRepository.findByUserId(userId)
                .orElseThrow(NoSuchElementException::new);
    }
}
