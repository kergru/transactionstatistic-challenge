package com.kgr.challenge.transactionstatistics.service;

import com.kgr.challenge.transactionstatistics.repository.InMemoryTransactionStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    Instant now = Instant.now();

    @Mock
    InMemoryTransactionStatisticsRepository repository;

    TransactionService service;

    @BeforeEach
    void setup() {
        service = new TransactionService(repository, Clock.fixed(now, ZoneId.systemDefault()));
    }

    @Test
    void test_save() {

        service.saveTransaction(2d);

        verify(repository).save(now, 2d);
    }
}