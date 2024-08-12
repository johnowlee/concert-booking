package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.model.QueuePolicy;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class QueueWriter {

    private final QueueWriterRepository queueWriterRepository;

    public void addActiveToken(Queue queue) {
        queueWriterRepository.addUserToActiveSet(queue);
    }

    public void removeActiveToken(Queue queue) {
        queueWriterRepository.removeUserFromActiveSet(queue);
    }

    public void addWaitingToken(Queue queue) {
        queueWriterRepository.addUserToWaitingSortedSet(queue);
    }

    public void removeWaitingToken(Queue queue) {
        queueWriterRepository.removeUserFromWaitingSortedSet(queue);
    }

    public void createActiveKey(Queue queue) {
        queueWriterRepository.createUserTimeout(queue, QueuePolicy.MAX_WORKING_SEC.getValue(), TimeUnit.SECONDS);
    }
}
