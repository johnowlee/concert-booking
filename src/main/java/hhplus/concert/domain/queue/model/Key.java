package hhplus.concert.domain.queue.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Key {
    ACTIVE("ACTIVE"),
    WAITING("WAITING");

    private final String keyName;
}
