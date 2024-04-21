package hhplus.concert.api.queue.usecase;

import java.util.UUID;

public abstract class GetUUID {

    public static String excute(Long userId) {
        return UUID.randomUUID() + "_" + userId;
    }
}
