package hhplus.concert.domain.queue.components;

import hhplus.concert.domain.queue.model.Queue;
import hhplus.concert.domain.queue.repositories.QueueWriterRepository;
import hhplus.concert.domain.queue.support.monitor.QueueMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueWriter {

    private final QueueWriterRepository queueWriterRepository;

    public void addActiveToken(Queue queue) {
        queueWriterRepository.addTokenToSet(queue);
    }

    public void removeActiveToken(Queue queue) {
        queueWriterRepository.removeTokenFromSet(queue);
    }

    public void addWaitingToken(Queue queue) {
        queueWriterRepository.addTokenToSortedSet(queue);
    }

    public void removeWaitingToken(Queue queue) {
        queueWriterRepository.removeTokenFromSortedSet(queue);
    }

    public void createActiveKey(Queue queue, QueueMonitor queueMonitor) {
        queueWriterRepository.createTtlToken(queue, queueMonitor.getTtl().timeout(), queueMonitor.getTtl().timeUnit());
    }
}
