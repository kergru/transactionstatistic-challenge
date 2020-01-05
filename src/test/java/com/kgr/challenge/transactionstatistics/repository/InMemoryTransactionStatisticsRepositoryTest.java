package com.kgr.challenge.transactionstatistics.repository;

import com.kgr.challenge.transactionstatistics.model.Statistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class InMemoryTransactionStatisticsRepositoryTest {

    @Mock
    Clock clock;

    @InjectMocks
    private InMemoryTransactionStatisticsRepository repository;


    @Test
    void get_merged_statistics_from_single_bucket() {
        Instant now = Instant.now();
        repository.save(now, 22.3d);
        repository.save(now, 11.5d);

        Statistics statistics = repository.get(now, now);

        assertThat(statistics.getSum().doubleValue()).isEqualTo(33.80d);
        assertThat(statistics.getAvg().doubleValue()).isEqualTo(16.90d);
    }

    @Test
    void get_merged_statistics_from_multiple_bucket() {
        Instant now = Instant.now();
        repository.save(now.minusSeconds(1), 22.3d);
        repository.save(now, 11.5d);

        Statistics statistics = repository.get(now.minusSeconds(1), now);

        assertThat(statistics.getSum().doubleValue()).isEqualTo(33.80d);
        assertThat(statistics.getAvg().doubleValue()).isEqualTo(16.90d);
    }

    @Test
    void get_merged_statistics_in_boundaries() {
        Instant now = Instant.now();
        repository.save(now.minusSeconds(1), 22.3d);
        repository.save(now, 11.5d);
        repository.save(now.plusSeconds(1), 9.52d);

        Statistics statistics = repository.get(now, now);

        assertThat(statistics.getSum().doubleValue()).isEqualTo(11.50d);
        assertThat(statistics.getAvg().doubleValue()).isEqualTo(11.50d);
    }

    @Test
    void removeOutdatedStatistics() {
        Instant now = Instant.now();
        doReturn(now).when(clock).instant();
        repository.save(now.minusSeconds(61), 2d);
        repository.save(now.minusSeconds(60), 2d);
        repository.save(now.minusSeconds(59), 2d);

        repository.removeOutdatedStatistics();
        Statistics statistics = repository.get(now.minusSeconds(60), now);

        assertThat(statistics.getSum().doubleValue()).isEqualTo(4.00d);
        assertThat(statistics.getAvg().doubleValue()).isEqualTo(2.00d);
    }
}