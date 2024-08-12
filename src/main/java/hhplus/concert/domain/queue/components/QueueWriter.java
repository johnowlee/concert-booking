package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import hhplus.concert.domain.queue.support.manager.TtlManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public void createActiveKey(Queue queue, TtlManager ttlManager) {
        queueWriterRepository.createUserTimeout(queue, ttlManager.getTtl().timeout(), ttlManager.getTtl().timeUnit());
    }
}
