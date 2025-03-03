package hhplus.concert.core.queue.domain.service.support.factory.score;

import org.springframework.stereotype.Component;

@Component
public class SystemScoreFactory implements ScoreFactory {
    @Override
    public long getTimeScore() {
        return System.currentTimeMillis();
    }
}
