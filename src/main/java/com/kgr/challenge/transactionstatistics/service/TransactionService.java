package com.kgr.challenge.transactionstatistics.service;


import com.kgr.challenge.transactionstatistics.repository.InMemoryTransactionStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final InMemoryTransactionStatisticsRepository repository;

    private final Clock clock;

    public void saveTransaction(double salesAmount) {
        repository.save(clock.instant(), salesAmount);
    }
}
