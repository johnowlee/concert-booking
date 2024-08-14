package hhplus.concert.domain.queue.support.factory.score;

import org.springframework.stereotype.Component;

@Component
public class SystemScoreFactory implements ScoreFactory {
    @Override
    public long getTimeScore() {
        return System.currentTimeMillis();
    }
}
