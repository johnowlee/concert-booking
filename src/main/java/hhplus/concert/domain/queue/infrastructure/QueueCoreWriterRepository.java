package hhplus.concert.domain.queue.infrastructure;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueueCoreWriterRepository implements QueueWriterRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue save(Queue queue) {
        return queueJpaRepository.save(queue);
    }
}
