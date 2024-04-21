package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueWriter {

    private final QueueWriterRepository queueWriterRepository;

    public Queue saveQueue(Queue queue) {
        return queueWriterRepository.save(queue);
    }
}
