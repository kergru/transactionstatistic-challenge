package com.kgr.challenge.transactionstatistics.service;

import com.kgr.challenge.transactionstatistics.model.Statistics;
import com.kgr.challenge.transactionstatistics.repository.InMemoryTransactionStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final InMemoryTransactionStatisticsRepository repository;

    private final Clock clock;

    public Statistics getStatistics() {
        Instant now = clock.instant();
        return repository.get(now.minusSeconds(60), now);
    }
}
