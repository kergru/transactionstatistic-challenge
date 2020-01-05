package com.kgr.challenge.transactionstatistics.endpoint;

import com.kgr.challenge.transactionstatistics.model.Statistics;
import com.kgr.challenge.transactionstatistics.service.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class GetStatisticEndpointTest {

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        StatisticService statisticService = mock(StatisticService.class);
        Statistics statistics = mock(Statistics.class);
        doReturn(BigDecimal.valueOf(14.10d).setScale(2)).when(statistics).getSum();
        doReturn(BigDecimal.valueOf(2d).setScale(2)).when(statistics).getAvg();
        doReturn(statistics).when(statisticService).getStatistics();

        this.mockMvc = standaloneSetup(new GetStatisticEndpoint(statisticService)).build();
    }

    @Test
    void get_should_return_statistic() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"total_sales_amount\":\"14.10\",\"average_amount_per_order\":\"2.00\"}"));
    }
}