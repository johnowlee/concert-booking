package hhplus.concert.domain.queue.repositories;

import java.util.Set;

public interface QueueReaderRepository {

    Long getActiveUserCount(String activeUserKey);

    Boolean isActiveUser(String activeUserKey, String token);
    Set<String> getWaitingUsersByRange(String waitingUserKey, long start, long end);

    Long getWaitingUserRank(String waitingUserKey, String token);
    Double getWaitingUserScore(String waitingUserKey, String token);
}
