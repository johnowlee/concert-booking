package hhplus.concert.domain.queue.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Queue {
    Key key;
    String token;
    long waitingNumber;
    double score;

    public String getKeyName() {
        return key.toString();
    }
}
