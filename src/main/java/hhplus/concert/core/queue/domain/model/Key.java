package hhplus.concert.core.queue.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Key {
    ACTIVE("booking:concert:active:users"),
    WAITING("booking:concert:waiting:users");

    private final String keyName;
}
