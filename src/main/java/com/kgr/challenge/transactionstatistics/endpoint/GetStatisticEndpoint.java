package com.kgr.challenge.transactionstatistics.endpoint;


import com.kgr.challenge.transactionstatistics.model.Statistics;
import com.kgr.challenge.transactionstatistics.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class GetStatisticEndpoint {

    private final StatisticService statisticService;

    @GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatisticResponse> getStatistic() {
        Statistics statistics = statisticService.getStatistics();
        return ok(getStatisticResponse(statistics));
    }

    private StatisticResponse getStatisticResponse(Statistics statistics) {
        return new StatisticResponse(statistics.getSum().toString(), statistics.getAvg().toString());
    }
}
