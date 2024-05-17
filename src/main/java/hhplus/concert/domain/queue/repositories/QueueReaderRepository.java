package hhplus.concert.domain.queue.repositories;

import java.util.Set;

public interface QueueReaderRepository {

    Long getSetSize(String key);

    Boolean containsValue(String key, String value);
    Set<String> getValuesByRange(String key, long start, long end);

    Long getRankByValue(String key, String value);
    Double getScoreByValue(String key, String value);
}
