package com.kgr.challenge.transactionstatistics.repository;

import com.kgr.challenge.transactionstatistics.model.Statistics;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTransactionStatisticsRepository {

    private static final int DISTANCE = 60;

    private final ConcurrentHashMap<Long, Statistics> storage;

    private final Clock clock;

    public InMemoryTransactionStatisticsRepository(Clock clock) {
        this.storage = new ConcurrentHashMap<>();
        this.clock = clock;
    }

    public Statistics get(Instant from, Instant to) {
        return storage.entrySet()
                .stream()
                .filter(entry -> isBucketKeyInTimeFrame(entry.getKey(), getBucketKey(from), getBucketKey(to)))
                .map(Map.Entry::getValue)
                .reduce(Statistics::merge)
                .orElseGet(Statistics::empty);
    }

    private long getBucketKey(Instant instant) {
        return instant.getEpochSecond();
    }

    private boolean isBucketKeyInTimeFrame(long bucketKey, long from, long to) {
        //return now.getEpochSecond() - bucketKey <= DISTANCE && now.getEpochSecond() - bucketKey >= 0;
        return (bucketKey >= from) && (bucketKey <= to);
    }

    public void save(Instant now, double salesAmount) {
        storage.merge(
                getBucketKey(now),
                Statistics.create(salesAmount),
                (statistics, emptyComputed) -> statistics.merge(emptyComputed)
        );
    }

    @Async
    @Scheduled(fixedRate = 1000l)
    public void removeOutdatedStatistics() {
        storage.entrySet()
                .stream()
                .filter(entry -> clock.instant().getEpochSecond() - entry.getKey() > DISTANCE)
                .map(Map.Entry::getKey)
                .forEach(storage::remove);
    }
}