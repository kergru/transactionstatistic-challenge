package com.kgr.challenge.transactionstatistics.service;

import com.kgr.challenge.transactionstatistics.model.Statistics;
import com.kgr.challenge.transactionstatistics.repository.InMemoryTransactionStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    Instant now = Instant.now();

    @Mock
    InMemoryTransactionStatisticsRepository repository;

    StatisticService service;

    @BeforeEach
    void setup() {
        service = new StatisticService(repository, Clock.fixed(now, ZoneId.systemDefault()));
    }

    @Test
    void test_get_statistics() {
        Statistics statistics = Statistics.create(4d);
        doReturn(statistics).when(repository).get(now.minusSeconds(60), now);

        Statistics actual = service.getStatistics();

        assertThat(actual.getSum().doubleValue()).isEqualTo(4.00d);
        assertThat(actual.getAvg().doubleValue()).isEqualTo(4.00d);
    }
}